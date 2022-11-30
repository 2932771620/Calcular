package ������;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    /*����׺���ʽתΪ��׺���ʽ
    ��(3+4)*5-6����׺���ʽתΪ��Ӧ��list
    String str=(3+4)*5-6;
    ���õ�����׺���ʽ��listת��Ϊ��׺���ʽ��list
     */

    public static List<String> parseSuffixExpreesionList(List<String> ls){
        try {
            //����һ��ջ��һ��Arraylist
            Stack<String> s1=new Stack<>();
            //��Ϊ�ڶ���ջû�г�ջ���������ԣ�����ArrayList���
            List<String> s2=new ArrayList<>();//�洢�м���
            //����ls
            for(String item:ls){
                //�����һ����������s2
                if(item.matches("[-+]?\\d+(?:\\.\\d+)?[-+]?")){
                    s2.add(item);
                }else if(item.equals("(")){
                    s1.push(item);
                }else if(item.equals(")")){
                    //����������ţ������ε���s1��ջ�������������ѹ��s2��ֱ������������Ϊֹ����ʱ����һ�������Ŷ���
                    while (!s1.peek().equals("(")){
                        s2.add(s1.pop());
                    }
                    s1.pop();//����С����
                }else{
                    //��item�����ȼ�С��s1ջ�������ȼ�����s1ջ����������������ٴ�ת���ٴ���s1���µ�ջ���������Ƚ�
                    //
                    while(s1.size()!=0&&Operation.getValue(s1.peek())>=Operation.getValue(item)){
                        s2.add(s1.pop());
                    }
                    //����Ҫ��itemѹ��ջ
                    s1.push(item);
                }
            }
            //��s1��ʣ�����������μ���s2��
            while (s1.size()!=0){
                s2.add(s1.pop());
            }
            return s2;//s2�����沨�����ʽ
        } catch (Exception e) {
            return null;
        }
    }
    //����׺���ʽתΪ��Ӧ��list
    public static List<String> toInfixExpressionList(String str){
        //����һ��List,�����׺���ʽ��Ӧ������
        List<String> list=new ArrayList<>();
        int index=0;//�൱��һ��ָ�룬���ڱ�����׺���ʽ���ַ���
        String s;//��������λ��ƴ�ӹ���
        char c;//ÿ������һ���ַ��ͷ��뵽c��
        do{
            //���c��һ��������,����.���ţ��ͼ��뵽ls�� 42*��43+��45-��47/��46������
            //���ڼ�������Ӧ�ķ���ֻ��+-*/,������Ascii��ȥ���
            //c����.����&&(c=str.charAt(index))!=46
            if((c=str.charAt(index))==42||(c=str.charAt(index))==43||(c=str.charAt(index))==45||(c=str.charAt(index))==47||(c=str.charAt(index))==40||(c=str.charAt(index))==41){
                list.add(""+c);
                index++;//ָ�����
            }else {//�����һ����������Ҫ���Ƕ�λ���͸�����
                s="";//�Ƚ�s�ƿ�
                while(index<str.length()&&(((c=str.charAt(index))>=48&&(c=str.charAt(index))<=57)||(c=str.charAt(index))==46)){
                    s+=c;
                    index++;
                }
                list.add(s);
            }
        }while (index<str.length());
        return list;
    }
    //��һ���沨�����ʽ�����ν����ݺ����������ArrayList��
    public static List<String> getListString(String suffixExpression){
        //��suffixExpression�ָ�
        String[] s = suffixExpression.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for(String str:s){
            list.add(str);
        }
        return list;
    }
    /*
       ��ɶ��沨�����ʽ������
        1.�����ҽ���ɨ�裬��3��4ѹ���ջ��
        2.����+������4������ֵ�����㣬����ջ
        3.��5��ջ
        4.����*�����������5��7��������Ľ����ջ
        5.��6��ջ
        6.����-�������������������������ս��
     */
    public static String calculate(List<String> ls){
        try {
            //������ջ��һ������
            Stack<String> stack=new Stack<>();
            //����ls
            for(String item:ls){
                //ʹ��������ʽȡ��
                /*
                ([-+]?\d+(?:\.\d+)?)��ʾ������
                ([1-9]\d*\.?\d*)|(0\.\d*[1-9])��ʾ�Ǹ���������
                 */
                if(item.matches("[-+]?\\d+(?:\\.\\d+)?")){//ƥ���Ƕ�λ��
                  //��ջ
                  stack.push(item);
                }else {
                    //���double����ȱ������
                    BigDecimal num2=new BigDecimal(stack.pop());
                    BigDecimal num1=new BigDecimal(stack.pop());
                    double res=0;
                    if(item.equals("+")){
                        res=num1.add(num2).doubleValue();
                    }else if(item.equals("-")){
                        res=num1.subtract(num2).doubleValue();
                    }else if(item.equals("*")){
                        res=num1.multiply(num2).doubleValue();
                    }else if(item.equals("/")) {
                        res=num1.divide(num2,15, RoundingMode.HALF_UP).doubleValue();
                    }
                    //res��ջ
                    stack.push(""+res);//������ֱ��ת�ַ���
                }
            }
            //�������ջ��Ľ������������
            return stack.pop();
        } catch (Exception e) {
            return "����ʽ��ʽ����";
        }
    }
}
//����һ�����Է���һ���������Ӧ�����ȼ�����
class Operation{
    private static int ADD=1;
    private static int SUB=1;
    private static int MUL=2;
    private static int DIV=2;
    //���ض�Ӧ�����ȼ�����
    public static int getValue(String operation){
        int result=0;
        switch (operation){
            case "+":
                result=ADD;
                break;
            case "-":
                result=SUB;
                break;
            case "*":
                result=MUL;
                break;
            case "/":
                result=DIV;
                break;
            default:
                break;
        }
        return result;
    }
}
