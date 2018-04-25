package Party.Members;

import Party.Item.Equipment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Party implements Iterable<PartyMember>{
    private static Party instance;

    private List<PartyMember> partyMembers;
    private List<Equipment> inventory;
    private int gold;

    private Party()
    {
        partyMembers = new ArrayList<>();
        inventory = new ArrayList<>();
        gold = 0;
    }

    public static Party getInstance() {
        if(instance == null) {
            instance = new Party();
        }
        return instance;
    }

    public void addMember(PartyMember member)
    {
        partyMembers.add(member);
        member.setParty(this);
    }

    public PartyMember getPlayer() {
        return partyMembers.get(0);
    }

    public PartyMember getPartyMember(int get)
    {
        return partyMembers.get(get);
    }

    public List<PartyMember> getPartyMembers() {
        return partyMembers;
    }

    public boolean allDead()
    {
        for(PartyMember p : this)
            if(p.isAlive())
                return false;
        return true;
    }

    public List<Equipment> getInventory()
    {
        return inventory;
    }

    public void addEquipment(Equipment equipment)
    {
        inventory.add(equipment);
    }

    public void removeEquipment(int index) {
        inventory.remove(index);
    }

    public Equipment getEquipment(int equipment)
    {
        return inventory.get(equipment);
    }

    public int getGold()
    {
        return this.gold;
    }

    public void addGold(int gold)
    {
        this.gold += gold;
    }

    @Override
    public Iterator<PartyMember> iterator() {
        return partyMembers.iterator();
    }
}
