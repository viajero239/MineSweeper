package sweeper;

public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;

    /* Object позволяет не привязываться к конкретной реализации. */
    public Object image;

    // this.ordinal() - порядковый номер текущего элемента в перечислении
    Box getNextNumberBox() {
        return Box.values()[this.ordinal() + 1];
    }
}
