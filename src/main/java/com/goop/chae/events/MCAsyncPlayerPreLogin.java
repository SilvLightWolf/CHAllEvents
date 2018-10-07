package com.goop.chae.events;

import com.goop.chae.Thrower;
import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class MCAsyncPlayerPreLogin {

    @api
    public static class CHAsyncPlayerPreLoginAPI extends AbstractEvent {

        public String getName() {
            return "async_player_pre_login";
        }

        public String docs() {
            return "Results: ALLOWED, KICK_BANNED, KICK_FULL, KICK_OTHER, KICK_WHITELIST";
        }

        public Version since() {
            return new SimpleVersion(1, 0, 0);
        }

        public boolean matches(Map<String, Construct> prefilter, BindableEvent e) throws PrefilterNonMatchException {
            return true;
        }

        public BindableEvent convert(CArray manualObject, Target t) {
            return null;
        }

        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            if(e instanceof CHAsyncPlayerPreLoginInterface){

                CHAsyncPlayerPreLoginInterface evt = (CHAsyncPlayerPreLoginInterface) e;
                Map<String, Construct> array = new HashMap<String, Construct>();
                Target t = Target.UNKNOWN;

                CArray arr = new CArray(t);

                array.put("kickmessage", new CString(evt.getKickMessage(), t));
                array.put("name", new CString(evt.getName(), t));
                array.put("loginresult", new CString(evt.getLoginResult().toString(), t));
                array.put("uuid", new CString(evt.getUUID(), t));

                array.put("macrotype", new CString("player", t));
                array.put("event_type", new CString(getName(), t));

                arr.set("hostname", evt.getAddress().getHostName(), t);
                arr.set("hostaddress", evt.getAddress().getHostAddress());

                array.put("host", arr);

                return array;
            }
            return null;
        }

        public Driver driver() {
            return Driver.EXTENSION;
        }

        public boolean modifyEvent(String key, Construct value, BindableEvent event) {
            if(key.equalsIgnoreCase("kickmessage")){
                ((CHAsyncPlayerPreLoginInterface)event).setKickMessage(value.getValue());
                return true;
            }else if(key.equalsIgnoreCase("result")){
                switch(value.getValue().toLowerCase()){ // ALLOWED, KICK_BANNED, KICK_FULL, KICK_OTHER, KICK_WHITELIST
                    case "allowed":
                        ((CHAsyncPlayerPreLoginInterface)event).setResult(AsyncPlayerPreLoginEvent.Result.ALLOWED);
                        return true;
                    case "kick_banned":
                        ((CHAsyncPlayerPreLoginInterface)event).setResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                        return true;
                    case "kick_full":
                        ((CHAsyncPlayerPreLoginInterface)event).setResult(AsyncPlayerPreLoginEvent.Result.KICK_FULL);
                        return true;
                    case "kick_other":
                        ((CHAsyncPlayerPreLoginInterface)event).setResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                        return true;
                    case "kick_whitelist":
                        ((CHAsyncPlayerPreLoginInterface)event).setResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);
                        return true;
                    default:
                        Thrower.throwCREIllegalException1(value.getValue());
                }
            }
            return false;
        }
    }

    public static class CHAsyncPlayerPreLoginEvent implements CHAsyncPlayerPreLoginInterface{

        AsyncPlayerPreLoginEvent e;

        public CHAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent e){ this.e = e; }

        public InetAddress getAddress() {
            return e.getAddress();
        }

        public String getKickMessage() {
            return e.getKickMessage();
        }

        public AsyncPlayerPreLoginEvent.Result getLoginResult() {
            return e.getLoginResult();
        }

        public String getName() {
            return e.getName();
        }

        public String getUUID() {
            return e.getUniqueId().toString();
        }

        public void setKickMessage(String msg) {
            e.setKickMessage(msg);
        }

        public void setResult(AsyncPlayerPreLoginEvent.Result result) {
            e.setLoginResult(result);
        }

        public Object _GetObject() {
            return null;
        }
    }

    public interface CHAsyncPlayerPreLoginInterface extends BindableEvent {
        public InetAddress getAddress();
        public String getKickMessage();
        public AsyncPlayerPreLoginEvent.Result getLoginResult();
        public String getName();
        public String getUUID();
        public void setKickMessage(String msg);
        public void setResult(AsyncPlayerPreLoginEvent.Result result);
    }

}
