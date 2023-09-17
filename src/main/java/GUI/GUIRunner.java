package GUI;

import GUI.GUIFrame;
public class GUIRunner {
    public static void main(String[] args) {
        System.out.println(GUIFrame.getInstance().hashCode());
    }

}