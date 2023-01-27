package backrooms.world.structure;

public enum WallType {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);
    private final int count;
    WallType(int count){
        this.count=count;
    }
    public int getCount(){
        return count;
    }
}
