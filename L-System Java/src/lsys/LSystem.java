package lsys;

import gpdraw.DrawingTool;
import gpdraw.SketchPad;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class LSystem{
    String vars = "X F";
    String consts = "+ - [ ]";
    String start = "X";
    RuleObj[] rules = parseRuleInput();
    double angle = 25.0;
    double length = 7.0;
    int level = 5;
    boolean varsDraw = true;
    SketchPad canvas = new SketchPad(700,700);
    DrawingTool pen = new DrawingTool(canvas);

    public void draw(int x, int y, double dir, int width, Color color){
        initPen(x, y, dir, width, color);
        drawStr(start, level);
    }

    public void initPen(int x, int y, double dir, int width, Color color){
        pen.up();
        pen.move(x,y);
        pen.setDirection(dir);
        pen.down();
        pen.setColor(color);
        pen.setWidth(width);
    }

    public void drawStr(String str, int level){
        ArrayDeque<Double> stack = new ArrayDeque<>();
        for(int i=0; i<str.length(); ++i){
            char c = str.charAt(i);
            if(consts.indexOf(c) != -1){
                if(c == '['){
                    stack.push(length);
                    stack.push(pen.getXPos());
                    stack.push(pen.getYPos());
                    stack.push(pen.getDirection());
                    stack.push((double)pen.getWidth());
                } else if(c == ']'){
                    double width = stack.pop();
                    double dir = stack.pop();
                    double y = stack.pop();
                    double x = stack.pop();
                    length = stack.pop();
                    pen.up();
                    pen.move(x,y);
                    pen.setDirection(dir);
                    pen.setWidth((int)width);
                    pen.down();
                } else {
                    doConstAction(c);
                }
            } else if(vars.indexOf(c) != -1){
                if(level == 0){
                    doVarAction();
                } else {
                    String rule = getRule(c);
                    drawStr(rule,level-1);
                }
            } else {
                System.out.println("Unknown character in drawStr: " + c);
                System.exit(0);
            }
        }
    }

    private void doConstAction(char cons){
       if(cons == '+') {
           pen.turnRight(angle);
       }else if(cons == '-'){
           pen.turnLeft(angle);
        }
    }

    private void doVarAction(){
        if(varsDraw){
            pen.forward(length);
        }
    }

    private String getRule(char var){
        int varIndex = vars.indexOf(var);
        if(varIndex == -1){
            System.out.println("Unknown character in getRule: " + var);
            System.exit(0);
            return null;
        } else {
            return rules[varIndex].getRule();
        }
    }
    private RuleObj[] parseRuleInput(){
        RuleObj[] result = new RuleObj[vars.length()];
        String[] splitInput = "X=F+[[X]-X]-F[-FX]+X, F=FF".split(",");
        for (String s : splitInput) {
            String rule = s;
            rule = rule.replace(" ", "");
            char var = rule.charAt(0);
            int varIndex = vars.indexOf(var);
            if (result[varIndex] == null) result[varIndex] = new RuleObj();
            result[varIndex].addRule(rule.substring(2));
        }
        return result;
    }
    static class RuleObj  {
        private final ArrayList<String> rules;
        private int ruleCount;
        private final Random random;

        public RuleObj(){
            rules = new ArrayList<>();
            ruleCount = 0;
            random = new Random();
        }

        public void addRule(String rule){
            rules.add(rule);
            ++ruleCount;
        }

        public String getRule(){
            int choice = random.nextInt(ruleCount);
            return rules.get(choice);
        }
    }

    public static void main(String[] args) {
        LSystem sys = new LSystem();
        sys.draw(0, -300, 90, 3, new Color(1,65,31));
    }
}