/*
 * SPDX-FileCopyrightText: Copyright Corsinvest Srl
 * SPDX-License-Identifier: GPL-3.0-only
 */
import it.corsinvest.proxmoxve.api.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {

    public static void main(String[] args) throws JSONException ,PveExceptionAuthentication{
        PveClient client = new PveClient("192.168.0.2", 8006);              
        client.setDebugLevel(999);
        if (client.login("root", "password")) {
            //version
            System.out.println(client.getVersion().version().getResponse().get("data"));

            // same for put/post/delete
            //loop nodes for
            JSONArray nodes = client.getNodes().index().getResponse().getJSONArray("data");
            for (int i = 0; i < nodes.length(); i++) {
                System.out.println(nodes.get(i));
            }

            //loop nodes for each
            PveClientBase.<JSONObject>JSONArrayToList(client.getNodes().index().getResponse().getJSONArray("data")).forEach((node) -> {
                System.out.println(node);
            });

            List<Object> commands = new ArrayList<>();
            commands.add("powershell");
            commands.add("-command");
            commands.add("echo");
            commands.add("test");
                   
            client.getNodes().get("cc01").getQemu().get(1006).getAgent().getExec().exec(commands);

            /*
            String nodeId = "cv-pve02";

            //loops vms qemu
            JSONArray vms = client.getNodes().get(nodeId).getQemu().vmlist().getResponse().getJSONArray("data");
            for (int i = 0; i < vms.length(); i++) {
                System.out.println(vms.get(i));
            }

            //loop snapshots
            JSONArray snapshots = client.getNodes().get(nodeId)
                    .getQemu().get(100).getSnapshot().snapshotList().getResponse().getJSONArray("data");
            for (int i = 0; i < snapshots.length(); i++) {
                System.out.println(snapshots.get(i));
            }

            //create snapshot
            client.getNodes().get(nodeId).getQemu().get(100).getSnapshot().snapshot("pippo");
            JSONObject retCreateSnap = client.getNodes().get(nodeId).getQemu().get(100).getSnapshot().snapshot("pippo").getResponse();

            //print UPID
            System.out.println(retCreateSnap.get("data"));

            //wait creation
            client.waitForTaskToFinish(retCreateSnap.getString("data"), 500, 10000);

            //delete snapshot
            Result retDeleSnap = client.getNodes().get(nodeId).getQemu().get(100).getSnapshot().get("pippo").delsnapshot();
            System.out.println(retDeleSnap.getResponse().get("data"));
             */
        }
    }
}
