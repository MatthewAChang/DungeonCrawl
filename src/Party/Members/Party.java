package Party.Members;

import Party.Item.Equipment;
import World.Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Party implements Iterable<PartyMember>{
    private List<PartyMember> partyMembers;
    private List<Equipment> inventory;
    private int gold;
    private Location location;

    public Party()
    {
        partyMembers = new ArrayList<>();
        inventory = new ArrayList<>();
        gold = 0;
        location = null;
    }

    public void addMember(PartyMember member)
    {
        partyMembers.add(member);
        member.setParty(this);
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

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    @Override
    public Iterator<PartyMember> iterator() {
        return partyMembers.iterator();
    }
}
