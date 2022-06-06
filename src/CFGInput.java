import java.util.ArrayList;
import java.util.Scanner;

public class CFGInput {
    //非终结符、终结符、生成式、起始符
    private ArrayList<String> NonTerminal;
    private ArrayList<String> Terminal;
    private ArrayList<Production> Production;
    private String Start;

    public CFGInput() {
        NonTerminal = new ArrayList<>();
        Terminal = new ArrayList<>();
        Production = new ArrayList<>();
        Start = "";
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String Start){
        this.Start=Start;
    }

    public ArrayList<String> getNonTerminal() {
        return NonTerminal;
    }

    public ArrayList<String> getTerminal() {
        return Terminal;
    }

    public ArrayList<Production> getProduction() {
        return Production;
    }
    public void MainInterface() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入终结符的集合(输入exit退出):");
        String TerminalChar = scanner.next();
        while (!TerminalChar.equals("exit")) {
            this.Terminal.add(TerminalChar);
            TerminalChar = scanner.next();
        }
        System.out.println("请输入非终结符的集合(输入exit退出):");
        String NonTerminalChar = scanner.next();
        while (!NonTerminalChar.equals("exit")) {
            NonTerminal.add(NonTerminalChar);
            NonTerminalChar = scanner.next();
        }
        System.out.println("请输入CFG文法的起始符(请注意起始符必须是非终结符集的元素):");
        Start = scanner.next();
        while (!NonTerminal.contains(Start)) {
            System.out.println("输入的符号在非终结符集中不存在，请重新输入:");
            Start = scanner.next();
        }
        System.out.println("接下来请输入生成式的集合(输入exit退出):");
        System.out.println("请注意，只能在提示输入生成式的左边的时候输入exit，否则会导致生成式的左边总数与右边总数不对等.");
        System.out.println("请输入生成式的左边(左边是单个的非终结符):");
        while (scanner.hasNext()) {
            String Left = scanner.next();
            if (Left.equals("exit")) {
                break;
            }
            if (!NonTerminal.contains(Left)) {
                System.out.println("你在生成式左边所输入的符号在非终结符集中不存在，请重新输入。");
                continue;
            }
            System.out.println("请输入生成式的右边:");
            String Right = scanner.next();
            if (Right.equals("exit")) {
                System.out.println("退出前只输入了生成式的左边，没有输入生成式的右边。请重新输入生成式的左右两边");
                continue;
            }
            else {
                Production.add(new Production(Left, Right));
            }
            System.out.println("请输入生成式的左边(左边是单个的非终结符):");
        }
        System.out.println("以下是你输入的CFG文法:");
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "非终结符的集合：" + NonTerminal +
                "\n终结符的集合：" + Terminal +
                "\n起始符：" + Start +
                "\n生成式的集合：" + Production;
    }

    public static void main(String[] args) {
        CFGInput cfgInput = new CFGInput();
        cfgInput.MainInterface();
        new DeclineEpsilon(cfgInput).run();
        new DeclineSingle(cfgInput).run();
        new DelNonGenerate(cfgInput).run();
        new DelNonArrival(cfgInput).run();
        System.out.println(cfgInput);
    }
}
