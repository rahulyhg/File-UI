package fileui;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import javax.swing.ImageIcon;

public class Model {

    private final Color default_color;
    private final Color[] colors;
    private final Font[] fonts;
    private final ImageIcon ic_user, ic_lock, ic_out, ic_back, ic_directory, ic_file,
            ic_trash, ic_upload, ic_download, ic_go, ic_change, 
            ic_info, ic_warning, ic_up, ic_down, ic_add, ic_refresh,
            app_icon;
    
    private final String appname = "File UI";

    private final static Model model = new Model();

    private Model() {
        app_icon = new ImageIcon(Model.class.getResource("resource/ic_appicon.png"));
        ic_user = new ImageIcon(Model.class.getResource("resource/ic_user.png"));
        ic_lock = new ImageIcon(Model.class.getResource("resource/ic_lock.png"));
        ic_out = new ImageIcon(Model.class.getResource("resource/ic_out.png"));
        ic_back = new ImageIcon(Model.class.getResource("resource/ic_back.png"));
        ic_directory = new ImageIcon(Model.class.getResource("resource/ic_directory.png"));
        ic_file = new ImageIcon(Model.class.getResource("resource/ic_file.png"));
        ic_trash = new ImageIcon(Model.class.getResource("resource/ic_trash.png"));
        ic_upload = new ImageIcon(Model.class.getResource("resource/ic_upload.png"));
        ic_download = new ImageIcon(Model.class.getResource("resource/ic_download.png"));
        ic_go = new ImageIcon(Model.class.getResource("resource/ic_go.png"));
        ic_info = new ImageIcon(Model.class.getResource("resource/ic_info.png"));
        ic_warning = new ImageIcon(Model.class.getResource("resource/ic_warning.png"));
        ic_change = new ImageIcon(Model.class.getResource("resource/ic_change.png"));
        ic_up = new ImageIcon(Model.class.getResource("resource/ic_up.png"));
        ic_down = new ImageIcon(Model.class.getResource("resource/ic_down.png"));
        ic_add = new ImageIcon(Model.class.getResource("resource/ic_add.png"));
        ic_refresh = new ImageIcon(Model.class.getResource("resource/ic_refresh.png"));
        
        colors = new Color[9];

        colors[0] = new Color(0x19BD9B);
        colors[1] = new Color(0x2DCC70);
        colors[2] = new Color(0x3598DC);
        colors[3] = new Color(0xE85862);
        colors[4] = new Color(0xF39B13);
        colors[5] = new Color(0xF1C40F);
        colors[6] = new Color(0xE77E23);
        colors[7] = new Color(0xE84C3D);
        colors[8] = new Color(0x9B58B5);

        default_color = colors[2];

        fonts = new Font[5];
        fonts[0] = new Font("Helvetica", Font.BOLD, 50);
        fonts[1] = new Font("Helvetica", Font.BOLD, 40);
        fonts[2] = new Font("Helvetica", Font.BOLD, 30);
        fonts[3] = new Font("Helvetica", Font.BOLD, 20);
        fonts[4] = new Font("Helvetica", Font.BOLD, 10);
    }

    public Color getColor(int i) {
        return getColors()[i];
    }

    public Color getRandomColor() {
        return getColors()[new Random().nextInt(getColors().length)];
    }

    public static Model getInstance() {
        return model;
    }

    /**
     * @return the appname
     */
    public String getAppname() {
        return appname;
    }

    /**
     * @return a font
     */
    public Font getFont(int i) {
        return getFonts()[i];
    }

    /**
     * @return a random font
     */
    public Font getRandomFont() {
        return getFonts()[new Random().nextInt(getFonts().length)];
    }

    /**
     * @return the default_color
     */
    public Color getDefault_color() {
        return default_color;
    }

    /**
     * @return the colors
     */
    public Color[] getColors() {
        return colors;
    }

    /**
     * @return the fonts
     */
    public Font[] getFonts() {
        return fonts;
    }

    /**
     * @return the ic_user
     */
    public ImageIcon getIc_user() {
        return ic_user;
    }

    /**
     * @return the ic_lock
     */
    public ImageIcon getIc_lock() {
        return ic_lock;
    }

    /**
     * @return the ic_out
     */
    public ImageIcon getIc_exit() {
        return ic_out;
    }

    /**
     * @return the ic_back
     */
    public ImageIcon getIc_back() {
        return ic_back;
    }

    /**
     * @return the ic_directory
     */
    public ImageIcon getIc_directory() {
        return ic_directory;
    }

    /**
     * @return the ic_file
     */
    public ImageIcon getIc_file() {
        return ic_file;
    }

    /**
     * @return the ic_trash
     */
    public ImageIcon getIc_trash() {
        return ic_trash;
    }

    /**
     * @return the ic_upload
     */
    public ImageIcon getIc_upload() {
        return ic_upload;
    }

    /**
     * @return the ic_download
     */
    public ImageIcon getIc_download() {
        return ic_download;
    }

    /**
     * @return the ic_go
     */
    public ImageIcon getIc_go() {
        return ic_go;
    }

    /**
     * @return the ic_info
     */
    public ImageIcon getIc_info() {
        return ic_info;
    }

    /**
     * @return the ic_warning
     */
    public ImageIcon getIc_warning() {
        return ic_warning;
    }

    /**
     * @return the ic_change
     */
    public ImageIcon getIc_change() {
        return ic_change;
    }

    /**
     * @return the ic_up
     */
    public ImageIcon getIc_up() {
        return ic_up;
    }

    /**
     * @return the ic_down
     */
    public ImageIcon getIc_down() {
        return ic_down;
    }

    /**
     * @return the ic_add
     */
    public ImageIcon getIc_add() {
        return ic_add;
    }

    /**
     * @return the ic_refresh
     */
    public ImageIcon getIc_refresh() {
        return ic_refresh;
    }

    /**
     * @return the app_icon
     */
    public ImageIcon getApp_icon() {
        return app_icon;
    }
}
