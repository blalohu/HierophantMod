package hierophant.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hierophant.HierophantMod;
import hierophant.characters.Hierophant.Enums;
import hierophant.powers.DeathKnellPower;

public class DeathKnell extends AbstractDynamicCard {
    public static final String ID = HierophantMod.makeID(DeathKnell.class.getSimpleName());
    private static final CardStrings cardStrings;
    public static final String IMG;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY;
    private static final AbstractCard.CardTarget TARGET;
    private static final AbstractCard.CardType TYPE;
    public static final AbstractCard.CardColor COLOR;
    private static final int COST = 1;
    private static final int MAGIC = 40;
    private static final int UPGRADE_PLUS_MAGIC = 20;

    public DeathKnell() {
        super(ID, IMG, 1, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 40;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DeathKnellPower(p, 2, this.upgraded), 2));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(20);
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        IMG = HierophantMod.makeCardPath("DeathKnell.png");
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        RARITY = CardRarity.RARE;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = Enums.COLOR_GOLD;
    }
}
