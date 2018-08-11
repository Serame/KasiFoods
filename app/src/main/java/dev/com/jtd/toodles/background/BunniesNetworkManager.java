package dev.com.jtd.toodles.background;

import java.util.ArrayList;

import dev.com.jtd.toodles.model.BunniesCartItem;
import dev.com.jtd.toodles.model.Bunny;

/**
 * Created by smoit on 2017/02/19.
 */

public interface BunniesNetworkManager {

    public void RequestIngredients(String ingredientsPerBun,String allIngredients);
}
