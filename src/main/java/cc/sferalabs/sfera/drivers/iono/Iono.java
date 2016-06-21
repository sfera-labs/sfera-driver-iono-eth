package cc.sferalabs.sfera.drivers.iono;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import cc.sferalabs.sfera.core.Configuration;
import cc.sferalabs.sfera.drivers.Driver;
import cc.sferalabs.sfera.drivers.iono.events.IonoAIEvent;
import cc.sferalabs.sfera.drivers.iono.events.IonoAVEvent;
import cc.sferalabs.sfera.drivers.iono.events.IonoDIEvent;
import cc.sferalabs.sfera.drivers.iono.events.IonoDOEvent;
import cc.sferalabs.sfera.events.Bus;

/**
 *
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public class Iono extends Driver {

	private String ionoId;
	private InetAddress ipAddress;
	private int port;

	private DatagramSocket monitorSocket;
	int lastPr = -1;

	public Iono(String id) {
		super(id);
	}

	@Override
	public boolean onInit(Configuration configuration) throws InterruptedException {
		String ip = configuration.get("ip", null);
		if (ip == null) {
			log.error("No IP address specified");
			return false;
		}
		try {
			ipAddress = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			log.error("Connection error", e);
			return false;
		}
		port = configuration.get("port", 7878);

		try {
			monitorSocket = new DatagramSocket(port);
			monitorSocket.setSoTimeout(60000);
		} catch (SocketException e) {
			log.error("Error initializing monitor socket", e);
			return false;
		}

		try {
			getState();
		} catch (Exception e) {
			log.error("Initialization error", e);
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void getState() throws Exception {
		try (DatagramSocket socket = new DatagramSocket()) {
			DatagramPacket replyPacket = send("state");

			JSONObject obj = getJsonObject(replyPacket);
			log.debug("State: {}", obj.toString());
			ionoId = (String) obj.get("id");
			for (int i = 1; i <= 6; i++) {
				Bus.postIfChanged(new IonoDOEvent(this, i, obj.get("DO" + i)));
				Bus.postIfChanged(new IonoDIEvent(this, i, obj.get("DI" + i)));
				if (i <= 4) {
					Bus.postIfChanged(new IonoAVEvent(this, i, obj.get("AV" + i)));
					Bus.postIfChanged(new IonoAIEvent(this, i, obj.get("AI" + i)));
				}
			}

		}
	}

	/**
	 * 
	 * @param packet
	 * @return
	 */
	private JSONObject getJsonObject(DatagramPacket packet) {
		return new JSONObject(new String(packet.getData()).trim());
	}

	/**
	 * 
	 * @param message
	 * @return
	 * @throws SocketException
	 */
	private DatagramPacket send(String message) throws IOException {
		try (DatagramSocket socket = new DatagramSocket()) {
			socket.setSoTimeout(5000);
			byte[] replyBuffer = new byte[512];
			byte[] stateReq = message.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(stateReq, stateReq.length, ipAddress,
					port);
			socket.send(sendPacket);
			DatagramPacket replyPacket = new DatagramPacket(replyBuffer, replyBuffer.length);
			socket.receive(replyPacket);

			return replyPacket;
		}
	}

	@Override
	public boolean loop() throws InterruptedException {
		try {
			byte[] updateBuffer = new byte[512];
			DatagramPacket updatePacket = new DatagramPacket(updateBuffer, updateBuffer.length);
			monitorSocket.receive(updatePacket);
			if (updatePacket.getAddress().equals(ipAddress)) {
				JSONObject obj = getJsonObject(updatePacket);
				log.debug("Update: {}", obj.toString());
				String id = (String) obj.get("id");
				if (id.equals(ionoId)) {
					int pr = ((Long) obj.get("pr")).intValue();
					if (lastPr != pr) {
						if (lastPr >= 0 && (lastPr + 1) % 10 != pr) {
							// missed something
							getState();
						}

						String pin = ((String) obj.opt("pin"));
						if (pin != null) {
							int index = Integer.parseInt(pin.substring(2, 3));
							Object value = obj.opt("val");

							if (pin.charAt(0) == 'D') {
								if (pin.charAt(1) == 'O') {
									Bus.postIfChanged(new IonoDOEvent(this, index, value));
								} else {
									Bus.postIfChanged(new IonoDIEvent(this, index, value));
								}
							} else {
								if (pin.charAt(1) == 'V') {
									Bus.postIfChanged(new IonoAVEvent(this, index, value));
								} else {
									Bus.postIfChanged(new IonoAIEvent(this, index, value));
								}
							}
						}
					}

					lastPr = pr;
				}
			}

			return true;

		} catch (InterruptedException e) {
			throw e;

		} catch (SocketTimeoutException e) {
			log.error("Timeout");
			return false;

		} catch (Exception e) {
			log.error("Exception in loop", e);
			return false;
		}
	}

	@Override
	public void onQuit() {
		try {
			monitorSocket.close();
		} catch (Exception e) {
		}
	}

	/**
	 * Sets digital output DO1 to the specified value.
	 * 
	 * @param val
	 *            {@code true} to close the output relay, {@code false} to open
	 *            it
	 * @return {@code true} if the command succeeded, {@code false} otherwise
	 */
	public boolean setDo1(Boolean val) {
		return setDo(1, val);
	}

	/**
	 * Sets digital output DO2 to the specified value.
	 * 
	 * @param val
	 *            {@code true} to close the output relay, {@code false} to open
	 *            it
	 * @return {@code true} if the command succeeded, {@code false} otherwise
	 */
	public boolean setDo2(Boolean val) {
		return setDo(2, val);
	}

	/**
	 * Sets digital output DO3 to the specified value.
	 * 
	 * @param val
	 *            {@code true} to close the output relay, {@code false} to open
	 *            it
	 * @return {@code true} if the command succeeded, {@code false} otherwise
	 */
	public boolean setDo3(Boolean val) {
		return setDo(3, val);
	}

	/**
	 * Sets digital output DO4 to the specified value.
	 * 
	 * @param val
	 *            {@code true} to close the output relay, {@code false} to open
	 *            it
	 * @return {@code true} if the command succeeded, {@code false} otherwise
	 */
	public boolean setDo4(Boolean val) {
		return setDo(4, val);
	}

	/**
	 * Sets digital output DO5 to the specified value.
	 * 
	 * @param val
	 *            {@code true} to close the output relay, {@code false} to open
	 *            it
	 * @return {@code true} if the command succeeded, {@code false} otherwise
	 */
	public boolean setDo5(Boolean val) {
		return setDo(5, val);
	}

	/**
	 * Sets digital output DO6 to the specified value.
	 * 
	 * @param val
	 *            {@code true} to close the output relay, {@code false} to open
	 *            it
	 * @return {@code true} if the command succeeded, {@code false} otherwise
	 */
	public boolean setDo6(Boolean val) {
		return setDo(6, val);
	}

	/**
	 * 
	 * @param idx
	 * @param val
	 * @return
	 */
	private boolean setDo(int idx, Boolean val) {
		String sVal;
		if (val == null) {
			sVal = "f";
		} else if (val) {
			sVal = "1";
		} else {
			sVal = "0";
		}
		return setPin("DO" + idx, sVal);
	}

	/**
	 * Sets analog output AO1 to the specified voltage value.
	 * 
	 * @param val
	 *            the voltage value to set (in V), allowed range: 0-10V
	 * @return {@code true} if the command succeeded, {@code false} otherwise
	 */
	public boolean setAo1(float val) {
		return setPin("AO1", Float.toString(val));
	}

	/**
	 * 
	 * @param pin
	 * @param val
	 * @return
	 */
	private boolean setPin(String pin, String val) {
		for (int i = 0; i < 3; i++) {
			try {
				DatagramPacket r = send(pin + "=" + val);
				String res = new String(r.getData());
				res = res.trim();
				if (res.equals("ok")) {
					return true;
				}
			} catch (IOException e) {
			}
		}

		return false;
	}
}