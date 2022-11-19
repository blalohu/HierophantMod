package hierophant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hierophant.HierophantMod;
import hierophant.characters.Hierophant;
import hierophant.powers.EnlightenedPower;
import hierophant.powers.PietyPower;

import static hierophant.HierophantMod.makeCardPath;
import static hierophant.powers.EnlightenedPower.PIETY_BONUS;
import static java.lang.Integer.min;

public class Zeal extends AbstractDynamicCard {

    public static final String ID = HierophantMod.makeID(Zeal.class.getSimpleName());
    public static final String IMG = makeCardPath("Zeal.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Hierophant.Enums.COLOR_GOLD;

    private static final int COST = 1;

    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 3;
    public static int PIETY;



    public Zeal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        piety = basePiety = PIETY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            int PIETY = 2 * min(m.currentBlock, damage);
                if (p.hasPower(EnlightenedPower.POWER_ID)) {
                    PIETY = PIETY_BONUS * PIETY / 100; //This is a manual Enlightened override since for some reason
            }                                          //the Enlightened Power in AbstractHierophantPower will not
                                                       //interact with Zeal.
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            if (PIETY > 0) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                        new PietyPower(p, p, PIETY), PIETY));
            }
        }


        @Override
        public void upgrade () {
            if (!upgraded) {
                upgradeName();
                upgradeDamage(UPGRADE_PLUS_DMG);
            }
        }
    }

