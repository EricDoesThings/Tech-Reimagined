package com.techreimagined.common.util.tooltip;

import com.google.common.collect.ForwardingList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* You are free to:
 * 
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material
 * for any purpose, even commercially.
 * The licensor cannot revoke these freedoms as long as you follow the license terms.
 * Under the following terms:

 * Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
 * ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
 * No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.
 * Notices:
 * 
 * You do not have to comply with the license for elements of the material in the public domain or where your use is permitted by an applicable exception or limitation.
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */
public class ToolTip extends ForwardingList<ToolTipLine> {

    private final List<ToolTipLine> delegate = new ArrayList<>();
    private final long delay;
    private long mouseOverStart;

    public ToolTip(ToolTipLine... lines) {
        this.delay = 0;
        Collections.addAll(delegate, lines);
    }

    public ToolTip(int delay, ToolTipLine... lines) {
        this.delay = delay;
        Collections.addAll(delegate, lines);
    }

    @Override
    protected final List<ToolTipLine> delegate() {
        return delegate;
    }

    public void onTick(boolean mouseOver) {
        if (delay == 0) {
            return;
        }
        if (mouseOver) {
            if (mouseOverStart == 0) {
                mouseOverStart = System.currentTimeMillis();
            }
        } else {
            mouseOverStart = 0;
        }
    }

    public boolean isReady() {
        if (delay == 0) {
            return true;
        }
        if (mouseOverStart == 0) {
            return false;
        }
        return System.currentTimeMillis() - mouseOverStart >= delay;
    }

    public void refresh() {}
}