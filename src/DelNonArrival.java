import java.util.ArrayList;
import java.util.Iterator;

public class DelNonArrival {
    private ArrayList<String> arrivalArray;
    private ArrayList<String> addArray;

    private CFGInput cfg;

    DelNonArrival(CFGInput cfgInput){
        arrivalArray=new ArrayList<>();
        addArray=new ArrayList<>();
        cfg=cfgInput;
    }

    void run(){
        ArrayList<String> nonTerminal=cfg.getNonTerminal();
        ArrayList<String> terminal=cfg.getTerminal();
        ArrayList<Production> P=cfg.getProduction();

        arrivalArray.clear();
        addArray.clear();
        addArray.add(cfg.getStart());

        while(true){
            for(Production temp:P){
                String left=temp.getLeft();
                String right=temp.getRight();

                if(addArray.contains(left))
                    for(int i=0;i<right.length();++i){
                        String word=right.substring(i,i+1);
                        if(!addArray.contains(word) && !arrivalArray.contains(word))
                            addArray.add(word);
                    }
            }

            if(addArray.isEmpty()) break;
            else{
                arrivalArray.addAll(addArray);
                addArray.clear();
            }
        }


        Iterator<String> iterator1=nonTerminal.iterator();
        while(iterator1.hasNext()){
            String tString =iterator1.next();
            if(!arrivalArray.contains(tString))
                iterator1.remove();
        }

        Iterator<String> iterator3=terminal.iterator();
        while(iterator3.hasNext()){
            String tString =iterator3.next();
            if(!arrivalArray.contains(tString))
                iterator3.remove();
        }

        Iterator<Production> iterator2=P.iterator();
        while(iterator2.hasNext()){
            Production tProduction=iterator2.next();
            String right=tProduction.getRight();
            boolean is_contain=true;
            if(!arrivalArray.contains(tProduction.getLeft()))
                is_contain=false;
            for(int i=0;i<right.length();++i){
                String word=right.substring(i,i+1);
                if((!nonTerminal.contains(word)) && (!terminal.contains(word))&&!word.equals("空")) {
                    is_contain=false;
                    break;
                }
            }
            if(is_contain==false)
                iterator2.remove();
        }
    }
}
