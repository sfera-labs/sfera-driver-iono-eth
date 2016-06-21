/**
 * 
 */
package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.Node;

/**
 * Event triggered when an analog current input's value changed.
 * 
 * @author Giampiero Baggiani
 * 
 * @sfera.event_id ai&lt;n&gt; where &lt;n&gt; is the input's index, e.g. "ai1"
 * @sfera.event_val val the current value read on the input, in mA
 *
 * @version 1.0.0
 *
 */
public class IonoAIEvent extends IonoAnalogEvent implements IonoInputEvent {

	/**
	 * 
	 * @param source
	 *            source node
	 * @param index
	 *            input index
	 * @param value
	 *            new value
	 */
	public IonoAIEvent(Node source, int index, Object value) {
		super(source, "ai" + index, value);
	}

}
