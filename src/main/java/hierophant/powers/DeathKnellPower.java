package hierophant.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import hierophant.HierophantMod;
import hierophant.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeathKnellPower extends AbstractPower implements CloneablePowerInterface, NonStackablePower {
    public static final Logger logger = LogManager.getLogger(HierophantMod.class.getName());
    public static final String POWER_ID = HierophantMod.makeID("DeathKnellPower");
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final Texture tex84;
    private static final Texture tex32;
    private int fervor;
    private boolean upgraded;

    public DeathKnellPower(AbstractCreature owner, int amount, boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.upgraded = upgraded;
        this.fervor = upgraded ? 60 : 40;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.description = DESCRIPTIONS[0];
        this.updateDescription();
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageType.NORMAL ? 0.0F : damage;
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfRound() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.amount == 1) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FervorPower(p, p, this.fervor), this.fervor));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }

        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }

    }

    public AbstractPower makeCopy() {
        return new DeathKnellPower(this.owner, this.amount, this.upgraded);
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        tex84 = TextureLoader.getTexture(HierophantMod.makePowerPath("death_knell_big.png"));
        tex32 = TextureLoader.getTexture(HierophantMod.makePowerPath("death_knell_small.png"));
    }
}
