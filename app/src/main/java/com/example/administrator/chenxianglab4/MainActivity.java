package com.example.administrator.chenxianglab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.calcualtor.R;

import java.math.BigDecimal;

//该计算器能实现两个double数之间的加减乘除及再运算，如2+3=5，-1.1=3.9，并且用了BigDecimal解决了精度丢失问题,
// 除不尽时保留三位小数，+/-按钮表示负号，比如按3再按+/-就是-3

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edit_input;
    boolean clear_flag=false;//用来判断是否按过了“=”进行过计算
    boolean ispc1=false,ispc2=false;//分别用来判断两个计算数是否含百分号
    Button but_0,but_1,but_2,but_3,but_4,but_5,but_6,but_7,but_8,but_9,
            but_allclear,but_negative,but_percent,but_point,but_plus,
            but_minus,but_multiply,but_divide,but_equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_input=(EditText)findViewById(R.id.edit_text);
        initbtn();

    }
    @Override
    public void onClick(View v) {
        String str =edit_input.getText().toString();
        switch (v.getId()) {
            case R.id.button_0:
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
            case R.id.button_point:
                if(clear_flag) {
                    clear_flag = false;
                    str = "";
                    edit_input.setText("");
                }
               edit_input.setText(str+((Button)v).getText().toString());
                break;
            case R.id.button_plu:
            case R.id.button_minus:
            case R.id.button_mul:
            case R.id.button_div:
                if(clear_flag){
                    clear_flag=false;
                    edit_input.setText("");
                }
                ;edit_input.setText(str+" "+((Button)v).getText().toString()+" ");//运算符前后加了空格便于判断
                break;
            case R.id.button_neg:
                if(!str.contains(" "))
                edit_input.setText("-"+str);
                else {
                    edit_input.setText(str.substring(0,str.indexOf(" ") + 3)+"-"+(str.substring(str.indexOf(" ") + 3)));
                }
                break;
            case R.id.button_ac:
                str = "";
                edit_input.setText("");
                break;
            case R.id.button_pc:
                if(clear_flag){
                    clear_flag=false;
                    edit_input.setText("");
                }
                else{
                    edit_input.setText(str+((Button)v).getText().toString());
                }
                break;
            case R.id.button_equ:
                getResult();
                break;
        }
    }

    private void getResult() {
        String st = edit_input.getText().toString();
        //如果输入为空
        if (st == null || st.equals("")) {
            return;
        }
        if (!st.contains(" ")) {
            return;
        }
        //计算过后重置clear_flag
        if (clear_flag) {
            clear_flag = false;
            return;
        }
        clear_flag = true;
        ispc1=false;
        ispc2=false;
        BigDecimal zero=new BigDecimal(0);
        BigDecimal result =new BigDecimal(0);
        String s1 = st.substring(0, st.indexOf(" "));
        String op = st.substring(st.indexOf(" ") + 1, st.indexOf(" ") + 2);
        String s2 = st.substring(st.indexOf(" ") + 3);
        if(s1.contains("%")){
            s1=s1.substring(0,s1.indexOf("%"));
            ispc1=true;
        }
        if(s2.contains("%")){
            s2=s2.substring(0,s2.indexOf("%"));
            ispc2=true;
        }
        BigDecimal d1=new BigDecimal(s1);
        BigDecimal d2=new BigDecimal(s2);
        BigDecimal a=new BigDecimal(100);
        if(ispc1==true){
            d1=d1.divide(a,2,BigDecimal.ROUND_HALF_DOWN);
        }
        if(ispc2==true){
            d2=d2.divide(a,2,BigDecimal.ROUND_HALF_DOWN);
        }
        //两个输入都不为空
        if (!s1.equals("") && !s2.equals("")) {
            if (op.equals("+")) {
                result =d1.add(d2);
            } else if (op.equals("-")) {
                result = d1.subtract(d2);
            } else if (op.equals("x")) {
                result = d1.multiply(d2);
            } else if (op.equals("÷")) {
                if (d2 == zero) {
                    result = zero;
                } else {
                    result = d1.divide(d2,3,BigDecimal.ROUND_HALF_DOWN);
                }
            }
            edit_input.setText(result + "");
        }
        //第二个输入为空
        else if(!s1.equals("")&&s2.equals("")){
                edit_input.setText(st);
            }
            //第一个输入为空，进行再运算
            else if(s1.equals("")&&!s2.equals("")){
                BigDecimal d3=new BigDecimal(s2);
                if(op.equals("+")){
                    result.add(d3);
                }else if(op.equals("-")){
                    result.subtract(d3);
                }else if(op.equals("x")){
                    result.multiply(d3);
                }else if(op.equals("÷")){
                    result.divide(d3,3,BigDecimal.ROUND_HALF_DOWN);
                }
                    edit_input.setText(result+"");
            }else{
                edit_input.setText("");
            }
    }

    private void initbtn(){
        but_0=(Button)findViewById(R.id.button_0);
        but_1=(Button)findViewById(R.id.button_1);
        but_2=(Button)findViewById(R.id.button_2);
        but_3=(Button)findViewById(R.id.button_3);
        but_4=(Button)findViewById(R.id.button_4);
        but_5=(Button)findViewById(R.id.button_5);
        but_6=(Button)findViewById(R.id.button_6);
        but_7=(Button)findViewById(R.id.button_7);
        but_8=(Button)findViewById(R.id.button_8);
        but_9=(Button)findViewById(R.id.button_9);
        but_allclear=(Button)findViewById(R.id.button_ac);
        but_negative=(Button)findViewById(R.id.button_neg);
        but_percent=(Button)findViewById(R.id.button_pc);
        but_point=(Button)findViewById(R.id.button_point);
        but_plus=(Button)findViewById(R.id.button_plu);
        but_minus=(Button)findViewById(R.id.button_minus);
        but_multiply=(Button)findViewById(R.id.button_mul);
        but_divide=(Button)findViewById(R.id.button_div);
        but_equal=(Button)findViewById(R.id.button_equ);

        but_0.setOnClickListener(this);
        but_1.setOnClickListener(this);
        but_2.setOnClickListener(this);
        but_3.setOnClickListener(this);
        but_4.setOnClickListener(this);
        but_5.setOnClickListener(this);
        but_6.setOnClickListener(this);
        but_7.setOnClickListener(this);
        but_8.setOnClickListener(this);
        but_9.setOnClickListener(this);
        but_allclear.setOnClickListener(this);
        but_negative.setOnClickListener(this);
        but_percent.setOnClickListener(this);
        but_point.setOnClickListener(this);
        but_plus.setOnClickListener(this);
        but_minus.setOnClickListener(this);
        but_multiply.setOnClickListener(this);
        but_divide.setOnClickListener(this);
        but_equal.setOnClickListener(this);
    }
}
