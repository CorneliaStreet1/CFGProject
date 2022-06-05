import java.util.ArrayList;

public class DeclineSingle {
    CFGInput cfg;
    ArrayList<Integer>[] map;
    ArrayList<String>[] single;

    public DeclineSingle(CFGInput cfg){
        this.cfg=cfg;
        map=new ArrayList[cfg.getNonTerminal().size()];
        single=new ArrayList[cfg.getNonTerminal().size()];
        for(int i=0;i<cfg.getNonTerminal().size();i++){
            map[i]=new ArrayList<>();
            single[i]=new ArrayList<>();
        }
    }
    ///每个非终结符对应的生成式序号
    public void generateMap(){
        for(int i=0;i<cfg.getProduction().size();i++){
            int aa=cfg.getNonTerminal().indexOf(cfg.getProduction().get(i).getLeft());
            map[aa].add(i);
        }
    }
    //得到每个非终结符能到达的集合
    public void markSingle(){
        ArrayList<Integer> mark=new ArrayList<>();
        for(int i=0;i<cfg.getProduction().size();i++){
            if(cfg.getProduction().get(i).getRight().length()==1&&cfg.getNonTerminal().contains(cfg.getProduction().get(i).getRight())){
                mark.add(i);
            }
        }
        for(int i=0;i<cfg.getNonTerminal().size();i++){
            single[i].add(cfg.getNonTerminal().get(i));
            int now=0;
            while(single.length!=now){
                now=single.length;
                for(int j=0;j<mark.size();j++){
                    if(single[i].contains(cfg.getProduction().get(mark.get(j)).getLeft())&& !single[i].contains(cfg.getProduction().get(mark.get(j)).getRight()))
                        single[i].add(cfg.getProduction().get(mark.get(j)).getRight());
                }
            }
        }
        //删去单生成式
        for(int i=mark.size()-1;i>-1;i--){
            ///////////////???????????
            int a=mark.get(i);
            cfg.getProduction().remove(a);
            /*cfg.getProduction().remove(mark.get(i));*/

        }
    }
    //增加生成式让语言不变
    //////////要考虑重复？
    public void newSingle(){
        //非终结符号i和非终结符号j
        for(int j=0;j<cfg.getNonTerminal().size();j++){
        for(int i=0;i<cfg.getNonTerminal().size();i++){

                if(j!=i){
                    if(single[j].contains(cfg.getNonTerminal().get(i)))
                        for(int k=0;k<map[i].size();k++){
                            cfg.getProduction().add(new Production(cfg.getNonTerminal().get(j),cfg.getProduction().get(map[i].get(k)).getRight()));
                        }
                }
            }
        }
    }

    public void run(){
        markSingle();
        generateMap();
        newSingle();
    }
}
