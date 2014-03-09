/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileui;

import com.jcraft.jsch.SftpProgressMonitor;
import javax.swing.ProgressMonitor;

/**
 *
 * @author xiaoerge
 */
public class MyProgressMonitor implements SftpProgressMonitor 
{
    public ProgressMonitor monitor;
    public long count = 0;
    public long max = 0;

    public void init(int op, String src, String dest, long max) {
        this.max = max;
        monitor = new ProgressMonitor(null,
                ((op == SftpProgressMonitor.PUT)
                ? "put" : "get") + ": " + src,
                "", 0, (int) max);
        count = 0;
        percent = -1;
        monitor.setProgress((int) this.count);
        monitor.setMillisToDecideToPopup(1000);
    }
    private long percent = -1;

    public boolean count(long count) {
        this.count += count;

        if (percent >= this.count * 100 / max) {
            return true;
        }
        percent = this.count * 100 / max;

        monitor.setNote("Completed " + this.count + " (" + percent + "%) out of " + max + ".");
        monitor.setProgress((int) this.count);

        return !(monitor.isCanceled());
    }

    public void end() {
        monitor.close();
    }
}
