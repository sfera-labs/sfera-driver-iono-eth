/*-
 * +======================================================================+
 * iono
 * ---
 * Copyright (C) 2016 Sfera Labs S.r.l.
 * ---
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * -======================================================================-
 */

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
