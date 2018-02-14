package team.covertdragon.springfestival.module.fortune.fortunevalue.capability;

public interface IFortuneValue {
    public int getFortuneValue();

    public void setFortuneValue(int quality);

    public void addFortune(int quality);

    public void shrinkFortune(int quality);
}
