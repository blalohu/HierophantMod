//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package hierophant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
import java.util.Iterator;

public class MintersHammer extends AbstractDynamicCard {
    public static final String ID = HierophantMod.makeID(MintersHammer.class.getSimpleName());
    public static final String IMG = HierophantMod.makeCardPath("MintersHammer.png");
    private static final CardStrings cardStrings;
    private static final AbstractCard.CardRarity RARITY;
    private static final AbstractCard.CardTarget TARGET;
    private static final AbstractCard.CardType TYPE;
    public static final AbstractCard.CardColor COLOR;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 20;
    private static final int UPGRADE_PLUS_DAMAGE = 10;

    public MintersHammer() {
        super(ID, IMG, 2, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 20;
        this.isMultiDamage = true;
        this.cardsToPreview = new Doubloon();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int upgrades = 0;
        Iterator var1 = p.hand.group.iterator();

        while(true) {
            AbstractCard c;
            do {
                do {
                    if (!var1.hasNext()) {
                        for(int i = 0; i < upgrades; ++i) {
                            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AttackEffect.BLUNT_HEAVY));
                        }

                        return;
                    }

                    c = (AbstractCard)var1.next();
                } while(c.cardID != Doubloon.ID);
            } while(!c.canUpgrade());

            c.upgrade();
            c.superFlash();
            c.applyPowers();
            ++upgrades;
            Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

            while(var2.hasNext()) {
                AbstractCard masterCard = (AbstractCard)var2.next();
                if (masterCard.uuid.equals(c.uuid)) {
                    masterCard.upgrade();
                }
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(10);
            this.initializeDescription();
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        RARITY = CardRarity.RARE;
        TARGET = CardTarget.ALL;
        TYPE = CardType.ATTACK;
        COLOR = Enums.COLOR_GOLD;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}
