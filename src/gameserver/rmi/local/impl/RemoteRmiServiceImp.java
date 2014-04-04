package gameserver.rmi.local.impl;

import gameserver.rmi.local.RemoteRmiService;

/**
 *
 * @author caoxin
 */
public class RemoteRmiServiceImp implements RemoteRmiService {

    @Override
    public String returnJson() {
        return "hello world";
    }
}