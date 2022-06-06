import java.util.ArrayList;
import java.util.Iterator;

public class DelNonGenerate {
    private ArrayList<String> generateArray;
    private ArrayList<String> addArray;

    private CFGInput cfg;

    DelNonGenerate(CFGInput cfgInput){
        System.out.println("new");
        generateArray=new ArrayList<>();
        addArray=new ArrayList<>();
        cfg=cfgInput;
    }

    void run(){
        ArrayList<String> nonTerminal=cfg.getNonTerminal();
        ArrayList<String> terminal=cfg.getTerminal();
        ArrayList<Production> P=cfg.getProduction();

        generateArray.clear();
        addArray.clear();

        generateArray.addAll(terminal);
        //将generateArray填完
        while(true){
            //向addArray加入东西
            for(Production temp:P){
                boolean key=true;
                if(!generateArray.contains(temp.getLeft())){
                    String left=temp.getLeft();
                    String right=temp.getRight();
                    for(int i=0;i<right.length();++i){
                        String word=right.substring(i,i+1);
                        if(!generateArray.contains(word)) {
                            key = false;
                            break;
                        }
                    }
                    if(key) addArray.add(left);
                }
            }
            //将addArray加入generateArray
            if(addArray.isEmpty()) break;
            else{
                generateArray.addAll(addArray);
                addArray.clear();
            }
        }




        Iterator<String> iterator1=nonTerminal.iterator();
        while(iterator1.hasNext()){
            String tString=iterator1.next();
            if(!generateArray.contains(tString))
                iterator1.remove();//由于remove会破坏Arraylist的迭代器，所以要用这种方法remove，自己去看next和remove的作用
        }

        Iterator<Production> iterator2=P.iterator();
        while(iterator2.hasNext()){
            Production tProduction=iterator2.next();
            String right=tProduction.getRight();
            boolean is_contain=true;
            if(!generateArray.contains(tProduction.getLeft()))
                is_contain=false;
            for(int i=0;i<right.length();++i){
                String word=right.substring(i,i+1);
                if(!nonTerminal.contains(word) && !terminal.contains(word)&&!word.equals("空")) {
                    is_contain=false;
                    break;
                }
            }
            if(is_contain==false)
                iterator2.remove();
        }
    }
}
