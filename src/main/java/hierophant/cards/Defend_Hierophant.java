package hierophant.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import hierophant.HierophantMod;
import hierophant.characters.Hierophant;

import static hierophant.HierophantMod.makeCardPath;


public class Defend_Hierophant extends AbstractTitheCard {


    public static final String ID = HierophantMod.makeID(Defend_Hierophant.class.getSimpleName());
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public static final String IMG = makeCardPath("Defend.png");

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Hierophant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    public Defend_Hierophant() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 5;
        titheOnlyWhenUpgraded = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            payTithe();
        }
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }



    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}

