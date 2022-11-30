package 计算器;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class calculator extends JFrame implements ActionListener {
    //输入和输出窗口的控件

    private JPanel jp_middle=new JPanel();
    private JTextField input_text=new JTextField();
    private JButton c=new JButton("c");
    private JTextField out_text=new JTextField();
    //20个按钮的控件
    private JPanel jp_south=new JPanel();
    private JPanel jp_north=new JPanel();

    public calculator(){
        this.init();
        this.addAbove();
        this.addBelow();
    }

    /**
     * 初始化图形窗口，包括，添加图像标记，设计窗口大小，窗口是否可以关闭，可以伸缩，窗口的位置
     */
    public void init(){
        this.setTitle(Final.TITLE);
        this.setSize(Final.FRAME_W,Final.FRAME_H);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setLocation(Final.FRAME_Y,Final.FRAME_X);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void addAbove(){//上方窗口的输入窗口和输出窗口设置
        this.input_text.setPreferredSize(new Dimension(240,30));
        this.out_text.setPreferredSize(new Dimension(240,30));
        jp_north.add(input_text);
        jp_middle.add(out_text);
        this.add(jp_north,BorderLayout.NORTH);
        this.add(jp_middle,BorderLayout.CENTER);
    }
    //下方窗口的20个按钮
    public void addBelow(){
        String btn_text="()<C789+456-123*.0=/";//计算机键盘中可以出现的数字
        this.jp_south.setLayout(new GridLayout(5,4));
        for(int i=0;i<20;i++){
            String temp=btn_text.substring(i,i+1);//截取字符
            JButton btn=new JButton();//添加模块
            btn.setPreferredSize(new Dimension(60,44));//给模块赋值大小
            btn.setText(temp);//往模块赋值
            btn.setFont(new Font("粗体",Font.BOLD,20));//调节模块中字体的值的大小
            btn.addActionListener(this);//获取监听到的值
            jp_south.add(btn);
        }
        this.add(jp_south,BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        calculator calculator = new calculator();
        calculator.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {//进行时间监听
        String actionCommand = e.getActionCommand();//得到监听的值
        if("0123456789.()+-*/".indexOf(actionCommand)!=-1){//只有这些值才可以显示到文本输入框之中
            this.input_text.setText(input_text.getText()+actionCommand);//往文本输入框中添加输入的值，并且不可以被覆盖
            this.input_text.setHorizontalAlignment(JTextField.RIGHT);//从右开始
            if(this.input_text.getText().equals("")){
                this.out_text.setText("0");

            }
            else {
                String getInput_text=this.input_text.getText();//获取文本的值
                /*
                调用PolandNotation里的函数进行计算
                 */
                String calculate = PolandNotation.calculate(PolandNotation.parseSuffixExpreesionList(PolandNotation.toInfixExpressionList(getInput_text)));
                this.out_text.setText(calculate);
                this.out_text.setHorizontalAlignment(JTextField.RIGHT);//从右开始
            }
        }else if (actionCommand.equals("=")){//如果获取到等号后
              String getInput_text=this.input_text.getText();//获取文本的值
                /*
                调用PolandNotation里的函数进行计算
                 */
              String calculate = PolandNotation.calculate(PolandNotation.parseSuffixExpreesionList(PolandNotation.toInfixExpressionList(getInput_text)));
              this.out_text.setText(calculate);
              this.out_text.setHorizontalAlignment(JTextField.RIGHT);//从右开始
             this.input_text.setText("");
        } else if (actionCommand.equals("C")) {//清空输入输出栏
            this.input_text.setText("");
            this.out_text.setText("");
        }else if(actionCommand.equals("<")) {
            int length=this.input_text.getText().length();//找出长度
            if(length>=1){//判断是否大于等于1，避免造成空指针异常
            String input=this.input_text.getText().substring(0,--length);//截取字符长度
            this.input_text.setText(input);
            }
        }
    }
}
