package ������;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class calculator extends JFrame implements ActionListener {
    //�����������ڵĿؼ�

    private JPanel jp_middle=new JPanel();
    private JTextField input_text=new JTextField();
    private JButton c=new JButton("c");
    private JTextField out_text=new JTextField();
    //20����ť�Ŀؼ�
    private JPanel jp_south=new JPanel();
    private JPanel jp_north=new JPanel();

    public calculator(){
        this.init();
        this.addAbove();
        this.addBelow();
    }

    /**
     * ��ʼ��ͼ�δ��ڣ����������ͼ���ǣ���ƴ��ڴ�С�������Ƿ���Թرգ��������������ڵ�λ��
     */
    public void init(){
        this.setTitle(Final.TITLE);
        this.setSize(Final.FRAME_W,Final.FRAME_H);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setLocation(Final.FRAME_Y,Final.FRAME_X);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void addAbove(){//�Ϸ����ڵ����봰�ں������������
        this.input_text.setPreferredSize(new Dimension(240,30));
        this.out_text.setPreferredSize(new Dimension(240,30));
        jp_north.add(input_text);
        jp_middle.add(out_text);
        this.add(jp_north,BorderLayout.NORTH);
        this.add(jp_middle,BorderLayout.CENTER);
    }
    //�·����ڵ�20����ť
    public void addBelow(){
        String btn_text="()<C789+456-123*.0=/";//����������п��Գ��ֵ�����
        this.jp_south.setLayout(new GridLayout(5,4));
        for(int i=0;i<20;i++){
            String temp=btn_text.substring(i,i+1);//��ȡ�ַ�
            JButton btn=new JButton();//���ģ��
            btn.setPreferredSize(new Dimension(60,44));//��ģ�鸳ֵ��С
            btn.setText(temp);//��ģ�鸳ֵ
            btn.setFont(new Font("����",Font.BOLD,20));//����ģ���������ֵ�Ĵ�С
            btn.addActionListener(this);//��ȡ��������ֵ
            jp_south.add(btn);
        }
        this.add(jp_south,BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        calculator calculator = new calculator();
        calculator.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {//����ʱ�����
        String actionCommand = e.getActionCommand();//�õ�������ֵ
        if("0123456789.()+-*/".indexOf(actionCommand)!=-1){//ֻ����Щֵ�ſ�����ʾ���ı������֮��
            this.input_text.setText(input_text.getText()+actionCommand);//���ı����������������ֵ�����Ҳ����Ա�����
            this.input_text.setHorizontalAlignment(JTextField.RIGHT);//���ҿ�ʼ
            if(this.input_text.getText().equals("")){
                this.out_text.setText("0");

            }
            else {
                String getInput_text=this.input_text.getText();//��ȡ�ı���ֵ
                /*
                ����PolandNotation��ĺ������м���
                 */
                String calculate = PolandNotation.calculate(PolandNotation.parseSuffixExpreesionList(PolandNotation.toInfixExpressionList(getInput_text)));
                this.out_text.setText(calculate);
                this.out_text.setHorizontalAlignment(JTextField.RIGHT);//���ҿ�ʼ
            }
        }else if (actionCommand.equals("=")){//�����ȡ���Ⱥź�
              String getInput_text=this.input_text.getText();//��ȡ�ı���ֵ
                /*
                ����PolandNotation��ĺ������м���
                 */
              String calculate = PolandNotation.calculate(PolandNotation.parseSuffixExpreesionList(PolandNotation.toInfixExpressionList(getInput_text)));
              this.out_text.setText(calculate);
              this.out_text.setHorizontalAlignment(JTextField.RIGHT);//���ҿ�ʼ
             this.input_text.setText("");
        } else if (actionCommand.equals("C")) {//������������
            this.input_text.setText("");
            this.out_text.setText("");
        }else if(actionCommand.equals("<")) {
            int length=this.input_text.getText().length();//�ҳ�����
            if(length>=1){//�ж��Ƿ���ڵ���1��������ɿ�ָ���쳣
            String input=this.input_text.getText().substring(0,--length);//��ȡ�ַ�����
            this.input_text.setText(input);
            }
        }
    }
}
