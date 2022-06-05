import java.util.ArrayList;

public class DeclineEpsilon {
    CFGInput cfg;
    ArrayList<String> empty;
    ArrayList<Integer>[] map;

    public DeclineEpsilon(CFGInput cfg){
        this.cfg=cfg;
        empty =new ArrayList<>();
        empty.add("空");////只考虑空
        map=new ArrayList[cfg.getNonTerminal().size()];
        //////////////////
        for(int i=0;i<cfg.getNonTerminal().size();i++){
            map[i]=new ArrayList<>();
        }
    }

    public void findEmpty(){
        int now=0;
        while(now!=empty.size()){
            now=empty.size();
            //每个非终结符号开始
            for(int i=0;i<cfg.getNonTerminal().size();i++){
                //empty里面没有
                if(!empty.contains(cfg.getNonTerminal().get(i))){
                    //遍历此非终结符能用的生成式
                    boolean isEmpty=false;
                    for(Integer a:map[i]){
                        if(contains(cfg.getProduction().get(a).getRight())){
                            isEmpty=true;
                            break;
                        }
                    }
                    if(isEmpty)
                        empty.add(cfg.getNonTerminal().get(i));
                }
            }
        }
    }

    public void declineEmpty(){
        //先记录原本生成式个数
        int origin=cfg.getProduction().size();
        //每个可致空符号
        for(int i=0;i<empty.size()-1;i++){
            //一个可致空的所有生成式
            for(int j=0,k=cfg.getNonTerminal().indexOf(empty.get(i+1));j<map[k].size();j++){////empty[0]==kong
                //一个生成式,map[k].get(j)某个生成式序号
                //全部字符转字符串
                ArrayList<Integer> pos=new ArrayList<>();
                char[] cha=cfg.getProduction().get(map[k].get(j)).getRight().toCharArray();
                String[] strings=new String[cha.length];
                //记录致空符号位置
                for(int x=0;x<cha.length;x++){
                    strings[x]=String.valueOf(cha[x]);
                    if(empty.contains(strings[x]))
                        pos.add(x);
                }
                int total=(int)Math.pow(2,(double)pos.size())-1;
                //添上total个生成式
                for(int y=0;y<total;y++){
                    int num=total-y-1;
                    int loc=0;
                    StringBuilder sb=new StringBuilder();
                    for(int n=0;n<pos.size();n++){
                        while(loc<pos.get(n))
                            sb.append(strings[loc++]);
                        if(num%2==1)
                            sb.append(strings[loc]);
                        num/=2;
                        loc++;
                    }
                    while(loc<strings.length){
                        sb.append(loc++);
                    }
                    cfg.getProduction().add(new Production(cfg.getProduction().get(map[k].get(j)).getLeft(),sb.toString()));
                }
                //判断最后一个是不是空
                if(strings.length==pos.size())
                    cfg.getProduction().remove(cfg.getProduction().size()-1);
            }
        }
        //删去原生成式里的空生成式
        for(int i=origin-1;i>-1;i--){
            if(cfg.getProduction().get(i).getRight().equals("空")){
                cfg.getProduction().remove(i);
            }
        }
        //如果语言包括空串
        if(empty.contains(cfg.getStart())){
            cfg.getProduction().add(0,new Production("ω",cfg.getStart()));
            cfg.getProduction().add(1,new Production("ω","空"));
            cfg.setStart("ω");
            cfg.getNonTerminal().add(0,"ω");
        }


    }
    ///每个非终结符对应的生成式序号
    public void generateMap(){
        for(int i=0;i<cfg.getProduction().size();i++){
            int aa=cfg.getNonTerminal().indexOf(cfg.getProduction().get(i).getLeft());
            map[aa].add(i);
        }
    }
    //一个字符串是否全是可致空符号
    public boolean contains(String s){
        for(int i=0;i<s.length();i++){
            String ss=String.valueOf(s.charAt(i));
            if(!empty.contains(ss))
                return false;
        }
        return true;
    }

    public void run(){
        generateMap();
        findEmpty();
        declineEmpty();
    }
}
