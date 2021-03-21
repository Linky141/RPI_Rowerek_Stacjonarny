package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UIFactory {

    private ArrayList<String> lblSchemaNames = new ArrayList<String>();

    public UIFactory(){
        lblSchemaNames.add("SampleUi");
        lblSchemaNames.add("ContrastUi");
        lblSchemaNames.add("ColorUi");
    }

    //region JButton
    public JButton NewJButton(String text, int x, int y, int w, int h, String UiSchema){
        JButton btn = new JButton(text);
        btn.setBounds(x,y,w,h);
        btn.setFocusPainted(false);
        btn = ChangeSchemaJButton(btn, UiSchema);

        return btn;
    }

    public JButton NewJButton(String text, int x, int y, int w, int h){
        return NewJButton(text, x, y, w ,h, "");
    }

    public JButton ReloadJbuttonUISchema(JButton btn, String UiSchema){
        btn = ChangeSchemaJButton(btn, UiSchema);
        return btn;
    }


    private JButton ChangeSchemaJButton(JButton btn, String Schema){
        if(Schema.equals(lblSchemaNames.get(0))){
            btn.setBackground(Color.darkGray);
            btn.setForeground(Color.lightGray);
            btn.setBorder(BorderFactory.createMatteBorder(2,6,2,6,Color.lightGray));
        }
        else if(Schema.equals(lblSchemaNames.get(1))){

            btn.setBackground(Color.black);
            btn.setForeground(Color.white);
            btn.setBorder(BorderFactory.createMatteBorder(2,6,2,6,Color.white));
        }
        else if(Schema.equals(lblSchemaNames.get(2))){

            btn.setBackground(Color.blue);
            btn.setForeground(Color.pink);
            btn.setBorder(BorderFactory.createMatteBorder(2,6,2,6,Color.green));
        }
        return btn;
    }
    //endregion

    //region JLabel
    public JLabel NewJLabel(String text, int x, int y, int w, int h, String UiSchema){
        JLabel lbl = new JLabel(text);
        lbl.setBounds(x,y,w,h);
        lbl = ChangeSchemaJLabel(lbl, UiSchema);
        return lbl;
    }

    public JLabel ReloadJLabelUISchema(JLabel lbl, String UiSchema){
        lbl = ChangeSchemaJLabel(lbl, UiSchema);
        return lbl;
    }

    public JLabel NewJLabel(String text, int x, int y, int w, int h){
        return NewJLabel(text, x, y, w ,h, "");
    }


    private JLabel ChangeSchemaJLabel(JLabel lbl, String Schema){
        if(Schema.equals(lblSchemaNames.get(0))){
            lbl.setForeground(Color.lightGray);
        }
        else if(Schema.equals(lblSchemaNames.get(1))){
            lbl.setForeground(Color.white);
        }
        else if(Schema.equals(lblSchemaNames.get(2))){
            lbl.setForeground(Color.orange);
        }
        return lbl;
    }
    //endregion

    //region JProgressBar
    public JProgressBar NewJProgressBar(int max, int min, int val, int x, int y, int w, int h, String UiSchema){
        JProgressBar jpb = new JProgressBar();
        jpb.setBounds(x,y,w,h);
        jpb.setMinimum(min);
        jpb.setMaximum(max);
        jpb.setValue(40);
        jpb = ChangeSchemaJProgressBar(jpb, UiSchema);

        return jpb;
    }

    public JProgressBar ReloadJProgressBarUISchema(JProgressBar jpb, String UiSchema){
        jpb = ChangeSchemaJProgressBar(jpb, UiSchema);
        return jpb;
    }

    public JProgressBar NewJProgressBar(int max, int min, int val, int x, int y, int w, int h){
        return NewJProgressBar(max, min, val, x, y, w ,h, "");
    }

    public JProgressBar NewJProgressBar(int max, int min, int x, int y, int w, int h, String UiSchema){
        return NewJProgressBar(max, min, 0, x, y, w ,h, UiSchema);
    }

    public JProgressBar NewJProgressBar(int max, int min, int x, int y, int w, int h){
        return NewJProgressBar(max, min, 0, x, y, w ,h, "");
    }

    public JProgressBar NewJProgressBar(int x, int y, int w, int h, String UiSchema){
        return NewJProgressBar(0, 0, 0, x, y, w ,h, UiSchema);
    }

    public JProgressBar NewJProgressBar(int x, int y, int w, int h){
        return NewJProgressBar(0, 0, 0, x, y, w ,h, "");
    }



    private JProgressBar ChangeSchemaJProgressBar(JProgressBar jpb, String Schema){
        if(Schema.equals(lblSchemaNames.get(0))){
            jpb.setBackground(Color.darkGray);
            jpb.setForeground(Color.lightGray);
            jpb.setBorder(BorderFactory.createMatteBorder(6,1,6,1,Color.lightGray));
        }
        else if(Schema.equals(lblSchemaNames.get(1))){

            jpb.setBackground(Color.black);
            jpb.setForeground(Color.white);
            jpb.setBorder(BorderFactory.createMatteBorder(6,1,6,1,Color.white));
        }
        else if(Schema.equals(lblSchemaNames.get(2))){

            jpb.setBackground(Color.blue);
            jpb.setForeground(Color.pink);
            jpb.setBorder(BorderFactory.createMatteBorder(6,1,6,1,Color.yellow));
        }
        return jpb;
    }
    //endregion

    //region JSlider
    public JSlider NewJSlider(int max, int min, int val, int x, int y, int w, int h, String UiSchema){
        JSlider jsld = new JSlider();
        jsld.setBounds(x,y,w,h);
        jsld.setMinimum(max);
        jsld.setMaximum(min);
        jsld.setValue(val);
        jsld.setMajorTickSpacing(10);
        jsld.setMinorTickSpacing(1);
        jsld.setPaintTicks(true);
        jsld.setPaintLabels(true);
        //jsld.

        jsld = ChangeSchemaJSlider(jsld, UiSchema);

        return jsld;
    }

    public JSlider ReloadJSliderUISchema(JSlider jsld, String UiSchema){
        jsld = ChangeSchemaJSlider(jsld, UiSchema);
        return jsld;
    }

    public JSlider NewJSlider(int max, int min, int val, int x, int y, int w, int h){
        return NewJSlider(max, min, val, x, y, w ,h, "");
    }

    public JSlider NewJSlider(int max, int min, int x, int y, int w, int h, String UiSchema){
        return NewJSlider(max, min, 0, x, y, w ,h, UiSchema);
    }

    public JSlider NewJSlider(int max, int min, int x, int y, int w, int h){
        return NewJSlider(max, min, 0, x, y, w ,h, "");
    }

    public JSlider NewJSlider(int x, int y, int w, int h, String UiSchema){
        return NewJSlider(0, 0, 0, x, y, w ,h, UiSchema);
    }

    public JSlider NewJSlider(int x, int y, int w, int h){
        return NewJSlider(0, 0, 0, x, y, w ,h, "");
    }



    private JSlider ChangeSchemaJSlider(JSlider jsld, String Schema){
        if(Schema.equals(lblSchemaNames.get(0))){
            jsld.setBackground(Color.darkGray);
            jsld.setForeground(Color.lightGray);
            jsld.setBorder(BorderFactory.createMatteBorder(6,1,6,1,Color.lightGray));
        }
        else if(Schema.equals(lblSchemaNames.get(1))){

            jsld.setBackground(Color.black);
            jsld.setForeground(Color.white);
            jsld.setBorder(BorderFactory.createMatteBorder(6,1,6,1,Color.white));
        }
        else if(Schema.equals(lblSchemaNames.get(2))){

            jsld.setBackground(Color.blue);
            jsld.setForeground(Color.pink);
            jsld.setBorder(BorderFactory.createMatteBorder(6,1,6,1,Color.yellow));
        }
        return jsld;
    }
    //endregion
}
