package hierophant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hierophant.HierophantMod;
import hierophant.characters.Hierophant;

import static hierophant.HierophantMod.makeCardPath;


public class Strike_Hierophant extends AbstractTitheCard {

    public static final String ID = HierophantMod.makeID(Strike_Hierophant.class.getSimpleName());
    public static final String IMG = makeCardPath("Strike.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Hierophant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int DAMAGE = 6;
    //private static final int UPGRADE_PLUS_DMG = 3;

    public Strike_Hierophant() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        titheOnlyWhenUpgraded = true;
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            payTithe();
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
           this.rawDescription = UPGRADE_DESCRIPTION;
           initializeDescription();
        }
    }
}

