package 计算器;

import java.awt.*;

public class Final {
    public static final int FRAME_W = 300;//窗口宽
    public static final int FRAME_H = 350;//窗口高
    public static final int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;//屏幕的宽
    public static final int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;//屏幕的高
    public static final int FRAME_X = (SCREEN_H - FRAME_H) / 2;//窗口所位于的中心位置
    public static final int FRAME_Y = (SCREEN_W - FRAME_W) / 2;
    public static final String TITLE = "简易计算器";//窗口的标题
}
//    String regex="[()<C\\+\\-*.=/]";正则表达式
//    if(temp.matches(regex)){//去和其中的字母进行匹配
//        btn.setFont(new Font("粗体",Font.BOLD,20));
//
//    }

