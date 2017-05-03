package easy.ucnt;

import java.util.HashMap;

/**
 * Created by Lucio on 17/4/20.
 */

public interface UnreadDBHelper {

    HashMap<String, Unread> loadUnreadList();

    void clearUnread(Unread unread);

}
