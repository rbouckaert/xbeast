package beast.app.draw;

import java.util.ArrayList;
import java.util.List;

import xbeast.core.BEASTInterface;
import xbeast.core.BEASTObject;
import xbeast.core.Description;
import xbeast.core.Input;



@Description("Set of beastObjects to represent partially finished models in GUIs")
public class BEASTObjectSet extends BEASTObject {
    final public Input<List<BEASTInterface>> m_plugins = new Input<>("beastObject", "set of the beastObjects in this collection", new ArrayList<>());

    @Override
    public void initAndValidate() {
    }
}
