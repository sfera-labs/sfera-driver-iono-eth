/**
 * 
 */
package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.Node;

/**
 * Event triggered when a digital output's value changed.
 * 
 * @author Giampiero Baggiani
 * 
 * @sfera.event_id do&lt;n&gt; where &lt;n&gt; is the output's index, e.g. "do1"
 * @sfera.event_val boolean 'true' when the output relay is closed, 'false' when
 *                  open
 *
 * @version 1.0.0
 *
 */
public class IonoDOEvent extends IonoDigitalEvent implements IonoOutputEvent {

	/**
	 * 
	 * @param source
	 *            source node
	 * @param index
	 *            output index
	 * @param value
	 *            new value
	 */
	public IonoDOEvent(Node source, int index, Object value) {
		super(source, "do" + index, value);
	}

}
