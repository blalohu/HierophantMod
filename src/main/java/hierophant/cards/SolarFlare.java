package hierophant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import hierophant.HierophantMod;
import hierophant.characters.Hierophant;
import hierophant.powers.FervorPower;

import static hierophant.HierophantMod.makeCardPath;
import static java.lang.Math.ceil;

public class SolarFlare extends AbstractTitheCard {

    public static final String ID = HierophantMod.makeID(SolarFlare.class.getSimpleName());
    public static final String IMG = makeCardPath("SolarFlare.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Hierophant.Enums.COLOR_GOLD;

    private static final int COST = 5;
    private static final int DAMAGE = 20;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public SolarFlare() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    //@Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        payTithe();
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }
    @Override
    public void applyPowersToBlock() {
        super.applyPowersToBlock();
        int realBlock = this.block;
        if (AbstractDungeon.player.hasPower(FervorPower.POWER_ID)) {
            int amount = AbstractDungeon.player.getPower(FervorPower.POWER_ID).amount;
            this.block = (int) (ceil(block * (amount * 10 + 100)) / 100);
        }
        isBlockModified = block != realBlock;
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}

