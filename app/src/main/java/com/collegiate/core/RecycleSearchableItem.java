package com.collegiate.core;

public interface RecycleSearchableItem extends RecyclableItem {

    boolean matches(String searchText);

}
