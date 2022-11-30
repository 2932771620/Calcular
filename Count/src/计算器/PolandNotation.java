package 计算器;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    /*将中缀表达式转为后缀表达式
    将(3+4)*5-6的中缀表达式转为对应的list
    String str=(3+4)*5-6;
    将得到的中缀表达式的list转化为后缀表达式的list
     */

    public static List<String> parseSuffixExpreesionList(List<String> ls){
        try {
            //定义一个栈，一个Arraylist
            Stack<String> s1=new Stack<>();
            //因为第二个栈没有出栈操作，所以，就用ArrayList替代
            List<String> s2=new ArrayList<>();//存储中间结果
            //遍历ls
            for(String item:ls){
                //如果是一个数，则入s2
                if(item.matches("[-+]?\\d+(?:\\.\\d+)?[-+]?")){
                    s2.add(item);
                }else if(item.equals("(")){
                    s1.push(item);
                }else if(item.equals(")")){
                    //如果是右括号，则依次弹出s1的栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对左括号丢弃
                    while (!s1.peek().equals("(")){
                        s2.add(s1.pop());
                    }
                    s1.pop();//消除小括号
                }else{
                    //当item的优先级小于s1栈顶的优先级，将s1栈顶的运算符弹出，再次转入再次与s1中新的栈顶运算符相比较
                    //
                    while(s1.size()!=0&&Operation.getValue(s1.peek())>=Operation.getValue(item)){
                        s2.add(s1.pop());
                    }
                    //还需要将item压入栈
                    s1.push(item);
                }
            }
            //将s1中剩余的运算符依次加入s2中
            while (s1.size()!=0){
                s2.add(s1.pop());
            }
            return s2;//s2就是逆波兰表达式
        } catch (Exception e) {
            return null;
        }
    }
    //将中缀表达式转为对应的list
    public static List<String> toInfixExpressionList(String str){
        //定义一个List,存放中缀表达式对应的内容
        List<String> list=new ArrayList<>();
        int index=0;//相当于一个指针，用于遍历中缀表达式的字符串
        String s;//用于做多位数拼接工作
        char c;//每遍历到一个字符就放入到c中
        do{
            //如果c是一个非数字,或者.符号，就加入到ls中 42*，43+，45-，47/，46（。）
            //由于计算器相应的符号只有+-*/,所以用Ascii码去解决
            //c不是.符号&&(c=str.charAt(index))!=46
            if((c=str.charAt(index))==42||(c=str.charAt(index))==43||(c=str.charAt(index))==45||(c=str.charAt(index))==47||(c=str.charAt(index))==40||(c=str.charAt(index))==41){
                list.add(""+c);
                index++;//指针后移
            }else {//如果是一个数，则需要考虑多位数和浮点数
                s="";//先将s制空
                while(index<str.length()&&(((c=str.charAt(index))>=48&&(c=str.charAt(index))<=57)||(c=str.charAt(index))==46)){
                    s+=c;
                    index++;
                }
                list.add(s);
            }
        }while (index<str.length());
        return list;
    }
    //将一个逆波兰表达式，依次将数据和运算符放入ArrayList中
    public static List<String> getListString(String suffixExpression){
        //将suffixExpression分割
        String[] s = suffixExpression.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for(String str:s){
            list.add(str);
        }
        return list;
    }
    /*
       完成对逆波兰表达式的运算
        1.从左到右进行扫描，将3，4压入堆栈，
        2.遇到+，弹出4和三的值，计算，再入栈
        3.将5入栈
        4.遇到*运算符，弹出5和7，计算出的结果入栈
        5.将6入栈
        6.遇到-运算符，计算出结果，这就是最终结果
     */
    public static String calculate(List<String> ls){
        try {
            //创建给栈，一个即可
            Stack<String> stack=new Stack<>();
            //遍历ls
            for(String item:ls){
                //使用正则表达式取数
                /*
                ([-+]?\d+(?:\.\d+)?)表示所有数
                ([1-9]\d*\.?\d*)|(0\.\d*[1-9])表示非负的所有数
                 */
                if(item.matches("[-+]?\\d+(?:\\.\\d+)?")){//匹配是多位数
                  //入栈
                  stack.push(item);
                }else {
                    //解决double精度缺少问题
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
                    //res入栈
                    stack.push(""+res);//将数字直接转字符串
                }
            }
            //最后留在栈里的结果就是运算结果
            return stack.pop();
        } catch (Exception e) {
            return "运算式格式错误";
        }
    }
}
//增减一个可以返回一个运算符对应的优先级的类
class Operation{
    private static int ADD=1;
    private static int SUB=1;
    private static int MUL=2;
    private static int DIV=2;
    //返回对应的优先级数字
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
