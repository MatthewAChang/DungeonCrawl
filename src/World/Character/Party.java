package World.Character;

import World.Item.Equipment;

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

    public void addMembers(List<PartyMember> members) {
        this.partyMembers.addAll(members);
    }

    public void addMember(PartyMember member)
    {
        partyMembers.add(member);
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

    public int getPartySize() {
        return partyMembers.size();
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

    public boolean minusGold(int gold) {
        if(gold > this.gold)
            return false;
        this.gold -= gold;
        return true;
    }

    @Override
    public Iterator<PartyMember> iterator() {
        return partyMembers.iterator();
    }
}
