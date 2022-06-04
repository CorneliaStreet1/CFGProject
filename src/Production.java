public class Production {
    private String Left;
    private String Right;
    private String Arrow;
    public Production(String left, String right) {
        this.Left = left;
        this.Right = right;
        this.Arrow = "==>";
    }

    public String getRight() {
        return Right;
    }

    public void setRight(String right) {
        Right = right;
    }

    public String getArrow() {
        return Arrow;
    }

    public void setArrow(String arrow) {
        Arrow = arrow;
    }

    public String getLeft() {
        return Left;
    }

    public void setLeft(String left) {
        Left = left;
    }

    @Override
    public String toString() {
        return Left + Arrow + Right;
    }
}
