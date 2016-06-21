/**
 * 
 */
package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.Node;

/**
 * Event triggered when an analog voltage input's value changed.
 * 
 * @author Giampiero Baggiani
 * 
 * @sfera.event_id av&lt;n&gt; where &lt;n&gt; is the input's index, e.g. "av1"
 * @sfera.event_val val the voltage value read on the input, in V
 *
 * @version 1.0.0
 *
 */
public class IonoAVEvent extends IonoAnalogEvent implements IonoInputEvent {

	/**
	 * 
	 * @param source
	 *            source node
	 * @param index
	 *            input index
	 * @param value
	 *            new value
	 */
	public IonoAVEvent(Node source, int index, Object value) {
		super(source, "av" + index, value);
	}

}
