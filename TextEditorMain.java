import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class TextEditorMain {
    public static void main(String[] args) {
        new Texteditor();
    }
}

@SuppressWarnings("serial")
class Texteditor extends Frame implements ActionListener {
    TextArea ta = new TextArea();
    int pos1;
    String str = "", s3 = "", s2 = "", s4 = "", s32 = "", s6 = "", s7 = "", s8 = "", s9 = "";
    String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
    CheckboxMenuItem chkb = new CheckboxMenuItem("Word Wrap");

    public Texteditor() {
        MenuBar mb = new MenuBar();
        setLayout(new BorderLayout());
        add("Center", ta);
        setMenuBar(mb);
        Menu m1 = new Menu("File");
        Menu m2 = new Menu("Edit");
        Menu m3 = new Menu("Tools");
        Menu m4 = new Menu("Help");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);

        MenuItem mi1[] = {
            new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save As"), new MenuItem("Exit")
        };
        MenuItem mi2[] = {
            new MenuItem("Cut"), new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Delete"), new MenuItem("Select All"), new MenuItem("Time Stamp")
        };
        MenuItem mi4[] = { new MenuItem("About Texteditor") };

        for (MenuItem item : mi1) {
            m1.add(item);
            item.addActionListener(this);
        }
        for (MenuItem item : mi2) {
            m2.add(item);
            item.addActionListener(this);
        }

        m3.add(chkb);
        chkb.addActionListener(this);

        for (MenuItem item : mi4) {
            m4.add(item);
            item.addActionListener(this);
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setSize(600, 500);
        setTitle("Untitled - Texteditor");
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String arg = ae.getActionCommand();

        if (arg.equals("New")) {
            dispose();
            new Texteditor();
        }

        try {
            if (arg.equals("Open")) {
                FileDialog fd1 = new FileDialog(this, "Select File", FileDialog.LOAD);
                fd1.setVisible(true);
                String s4 = "";
                s2 = fd1.getFile();
                s3 = fd1.getDirectory();
                if (s2 == null || s3 == null) return;
                s32 = s3 + s2;
                File f = new File(s32);
                FileInputStream fii = new FileInputStream(f);
                int len = (int) f.length();
                for (int j = 0; j < len; j++) {
                    char s5 = (char) fii.read();
                    s4 = s4 + s5;
                }
                ta.setText(s4);
                fii.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (arg.equals("Save As")) {
                FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
                dialog1.setVisible(true);
                s7 = dialog1.getDirectory();
                s8 = dialog1.getFile();
                if (s7 == null || s8 == null) return;
                s9 = s7 + s8 + ".txt";
                s6 = ta.getText();
                byte buf[] = s6.getBytes();
                File f1 = new File(s9);
                FileOutputStream fobj1 = new FileOutputStream(f1);
                fobj1.write(buf);
                fobj1.close();
                this.setTitle(s8 + " - Texteditor File");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (arg.equals("Exit")) {
            System.exit(0);
        }

        if (arg.equals("Cut")) {
            str = ta.getSelectedText();
            int i = ta.getText().indexOf(str);
            ta.replaceRange("", i, i + str.length());
        }

        if (arg.equals("Copy")) {
            str = ta.getSelectedText();
        }

        if (arg.equals("Paste")) {
            pos1 = ta.getCaretPosition();
            ta.insert(str, pos1);
        }

        if (arg.equals("Delete")) {
            String msg = ta.getSelectedText();
            int i = ta.getText().indexOf(msg);
            ta.replaceRange("", i, i + msg.length());
        }

        if (arg.equals("Select All")) {
            String strText = ta.getText();
            ta.select(0, strText.length());
        }

        if (arg.equals("Time Stamp")) {
            GregorianCalendar gcalendar = new GregorianCalendar();
            String h = String.valueOf(gcalendar.get(Calendar.HOUR));
            String m = String.valueOf(gcalendar.get(Calendar.MINUTE));
            String s = String.valueOf(gcalendar.get(Calendar.SECOND));
            String date = String.valueOf(gcalendar.get(Calendar.DATE));
            String mon = months[gcalendar.get(Calendar.MONTH)];
            String year = String.valueOf(gcalendar.get(Calendar.YEAR));
            String hms = "Time - " + h + ":" + m + ":" + s + " Date - " + date + " " + mon + " " + year + " ";
            int loc = ta.getCaretPosition();
            ta.insert(hms, loc);
        }

        if (arg.equals("About Texteditor")) {
            new AboutDialog(this, "About Texteditor").setVisible(true);
        }
    }
}

class AboutDialog extends Dialog implements ActionListener {
    public AboutDialog(Frame parent, String title) {
        super(parent, title, true);
        setLayout(new FlowLayout());
        setSize(300, 150);
        add(new Label("Simple Text Editor created in Java using AWT."));
        Button ok = new Button("OK");
        add(ok);
        ok.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}
