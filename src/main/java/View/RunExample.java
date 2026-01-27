package View;

import Controller.Controller;
import Model.Exception.MyException;

public class RunExample extends Command{
    private Controller ctr;
    public RunExample(String key, String desc, Controller controller) {
        super(key, desc);
        this.ctr = controller;
    }
    @Override
    public void execute(){
        try{
            ctr.allStep();
        }
        catch(MyException e){
            System.out.println(e.getMessage());
        }
    }
}
