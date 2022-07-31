package com.example.mycalculator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result;
    int countL = 0, countR = 0;
    int countP = 0;
    int pointFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //绑定控件
        Button btn_0 = (Button) findViewById(R.id.btn_0);
        Button btn_1 = (Button) findViewById(R.id.btn_1);
        Button btn_2 = (Button) findViewById(R.id.btn_2);
        Button btn_3 = (Button) findViewById(R.id.btn_3);
        Button btn_4 = (Button) findViewById(R.id.btn_4);
        Button btn_5 = (Button) findViewById(R.id.btn_5);
        Button btn_6 = (Button) findViewById(R.id.btn_6);
        Button btn_7 = (Button) findViewById(R.id.btn_7);
        Button btn_8 = (Button) findViewById(R.id.btn_8);
        Button btn_9 = (Button) findViewById(R.id.btn_9);
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        Button btn_C = (Button) findViewById(R.id.btn_C);
        Button btn_LBracket = (Button) findViewById(R.id.btn_LBracket);
        Button btn_RBracket = (Button) findViewById(R.id.btn_RBracket);
        Button btn_divide = (Button) findViewById(R.id.btn_divide);
        Button btn_multiple = (Button) findViewById(R.id.btn_multiple);
        Button btn_subtract = (Button) findViewById(R.id.btn_subtract);
        Button btn_add = (Button) findViewById(R.id.btn_add);
        Button btn_equal = (Button) findViewById(R.id.btn_equal);
        Button btn_point = (Button) findViewById(R.id.btn_point);
        result = (TextView) findViewById(R.id.result);

        //注册监听器
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_C.setOnClickListener(this);
        btn_LBracket.setOnClickListener(this);
        btn_RBracket.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_multiple.setOnClickListener(this);
        btn_subtract.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_point.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String input = result.getText().toString();
        switch (view.getId()) {
            case R.id.btn_0:
                if (input.length() == 1 && input.charAt(0) == '0') {
                    //不动
                    result.setText(input);
                } else if (input.length() != 0 && input.charAt(input.length() - 1) == '÷') {
                    //弹提醒不能为0
                    Toast.makeText(MainActivity.this, "除数不能为0", Toast.LENGTH_SHORT).show();
                    result.setText(input);
                } else {
                    result.setText(input + ((Button) view).getText());
                }
                break;
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                result.setText(input + ((Button) view).getText());
                break;
            case R.id.btn_point:
                if (input.length() == 0) {
                    result.setText("0.");
                } else {
                    if (input.charAt(input.length() - 1) == '＋' || input.charAt(input.length() - 1) == '－' || input.charAt(input.length() - 1) == '×' || input.charAt(input.length() - 1) == '÷' || input.charAt(input.length() - 1) == '(') {
                        result.setText(input + "0.");
                        countP++;
                    } else if (input.charAt(input.length() - 1) == ')') {
                        result.setText(input);
                    } else {
                        result.setText(input + ((Button) view).getText());
                        countP++;
                    }
                }
                break;
            case R.id.btn_subtract:
                if(countP >= 2){
                    Toast.makeText(MainActivity.this, "表达式错误", Toast.LENGTH_SHORT).show();
                    result.setText("");
                    countP = 0;
                    countL = countR = 0;
                }
                if(input.length() != 0){
                    if(!isOperation(input.charAt(input.length() - 1))) {
                        result.setText(input + ((Button) view).getText());
                    }else{
                        result.setText(input);
                    }
                }else {
                    result.setText(input + ((Button) view).getText());
                }
                break;
            case R.id.btn_add:
            case R.id.btn_divide:
            case R.id.btn_multiple:
                if(countP >= 2){
                    Toast.makeText(MainActivity.this, "表达式错误", Toast.LENGTH_SHORT).show();
                    result.setText("");
                    countP = 0;
                    countL = countR = 0;
                }else{
                    countP = 0;
                }
                if(input.length() != 0){
                    if(!isOperation(input.charAt(input.length() - 1))) {
                        result.setText(input + ((Button) view).getText());
                    }else{
                        result.setText(input);
                    }
                }
                break;
            case R.id.btn_LBracket:
                Log.d("Here", "countL");
                ++countL;
                result.setText(input + ((Button) view).getText());
                break;
            case R.id.btn_RBracket:
                Log.d("Here", "countR");
                countR++;
                result.setText(input + ((Button) view).getText());
                break;
            case R.id.btn_C:
                result.setText("");
                break;
            case R.id.btn_delete:
                if (input.length() != 0) {
                    result.setText(input.substring(0, input.length() - 1));
                } else {
                    result.setText(input);
                }
                break;
            case R.id.btn_equal:
                Log.d("Here", "L=" + countL + "R=" + countR);
                if(countL != countR || countP >= 2){
                    Log.d("Here", "!=");
                    Toast.makeText(MainActivity.this, "表达式错误", Toast.LENGTH_SHORT).show();
                    result.setText("");
                    countL = countR = 0;
                    countP = 0;
                }else{
                    countL = countR = 0;
                    countP = 0;
                    result.setText(getResult(input).toString());
                }
                break;
        }
    }

    public static BigDecimal getResult(String str){
        return evalRPN(partOne(str));
    }
    //中缀转后缀
    public static List<String> partOne(String str) {
        List<String> list = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (isNumber(str.charAt(i))) {
                //是数字
                num.append(str.charAt(i));
                if (i == str.length() - 1 || ((i + 1) < str.length() && (isOperation(str.charAt(i + 1)) || isBracket(str.charAt(i + 1))))) {
                    //是最后一位，或者下一个是字符，就入队
                    list.add(num.toString());
                    num = new StringBuilder();
                }
            } else if (isBracket(str.charAt(i))) {
                //是括号
                if (str.charAt(i) == '(') {
                    //如果是左括号，入栈
                    stack.push(str.charAt(i));
                } else {
                    Character temp;
                    while ((temp = stack.pop()) != '(') {
                        list.add(temp.toString());
                    }
                }
            } else if (isOperation(str.charAt(i))) {
                //System.out.println("operation");
                //是操作符，比较优先级
                while (true) {
                    if (stack.isEmpty()) {
                        stack.push(str.charAt(i));
                        break;
                    } else if (priority(str.charAt(i)) < priority(stack.peek())) {
                        //栈内的优先级比要进去的高，取出来继续对比
                        list.add(stack.pop().toString());
                    } else {
                        //栈内优先级比进去的低，直接放进去
                        stack.push(str.charAt(i));
                        break;
                    }
                }
            }
        }
        while (!stack.isEmpty()) {
            list.add(stack.pop().toString());
        }
        return list;
    }

    public static BigDecimal evalRPN(List<String> tokens) {
        Stack<BigDecimal> stack = new Stack<BigDecimal>();
        for (String token : tokens) {
            if (isNumber(token.charAt(0))) {
                BigDecimal num = new BigDecimal(token);
                    stack.push(num);
            } else {
                    BigDecimal num2 = stack.pop();
                    BigDecimal num1 = stack.pop();
                    switch (token) {
                        case "＋":
                            stack.push(num1.add(num2));
                            break;
                        case "－":
                            stack.push(num1.subtract(num2));
                            break;
                        case "×":
                            stack.push(num1.multiply(num2));
                            break;
                        case "÷":
                            if (num2.signum() == 0) {
                                throw new RuntimeException("Error");
                            }
                            stack.push(num1.divide(num2, 2, RoundingMode.HALF_UP));
                            break;
                        default:
                    }

            }
        }
        return stack.peek();

    }

    public static boolean isOperation(char token) {
        return (token == '＋' || token == '－' || token == '×' || token == '÷');
    }

    public static boolean isBracket(char token) {
        return token == '(' || token == ')';
    }

    public static boolean isNumber(char token) {
        return !(token == '＋' || token == '－' || token == '×' || token == '÷' || token == '(' || token == ')');
    }

    public static int priority(char token) {
        //返回数字越大优先级越高
        int pri = 0;
        switch (token) {
            case '(':
                pri = 1;
                break;
            case '＋':
            case '－':
                pri = 2;
                break;
            case '×':
            case '÷':
                pri = 3;
                break;
        }
        return pri;
    }

}