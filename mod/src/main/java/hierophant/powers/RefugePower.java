package hierophant.powers;

import static hierophant.HierophantMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.interfaces.CloneablePowerInterface;
import hierophant.HierophantMod;
import hierophant.cards.Doubloon;
import hierophant.util.TextureLoader;

public class RefugePower extends AbstractPower implements CloneablePowerInterface {
    public static final Logger logger = LogManager.getLogger(HierophantMod.class.getName());
    public AbstractCreature source;

    public static final String POWER_ID = HierophantMod.makeID("RefugePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("refuge_big.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("refuge_small.png"));

    public RefugePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.description = DESCRIPTIONS[0];

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
       }
   }

    @Override
    public void onCardDraw(AbstractCard c) {
        AbstractPlayer p = AbstractDungeon.player;
        if (c.cardID == Doubloon.ID) {
            int blockAmount = p.getPower(RefugePower.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, blockAmount));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && !mo.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, p, blockAmount));
                }
            }

        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RefugePower(owner, source, amount);
    }
}
