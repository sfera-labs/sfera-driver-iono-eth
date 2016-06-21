/**
 * 
 */
package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.Node;

/**
 * Event triggered when a digital input's value changed.
 * 
 * @author Giampiero Baggiani
 * 
 * @sfera.event_id di&lt;n&gt; where &lt;n&gt; is the input's index, e.g. "di1"
 * @sfera.event_val boolean 'true' when the input is high, 'false' when low
 *
 * @version 1.0.0
 *
 */
public class IonoDIEvent extends IonoDigitalEvent implements IonoInputEvent {

	/**
	 * 
	 * @param source
	 *            source node
	 * @param index
	 *            input index
	 * @param value
	 *            new value
	 */
	public IonoDIEvent(Node source, int index, Object value) {
		super(source, "di" + index, value);
	}

}
