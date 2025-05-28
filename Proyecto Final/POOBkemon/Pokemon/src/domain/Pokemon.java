package domain;

import persistence.MovesRepository;
import persistence.StatsRepository;
import persistence.StatusRepository;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;

/**
 * The Pokemon class represents a Pokemon entity with various attributes and methods
 * for managing its states, statistics, and interactions during battles.
 * It includes support for customization, random stat generation, attacks, and status effects.
 */
public class Pokemon implements Serializable {

	// Basic Pokemon attributes
	public String name;
	public String idPokedex;
	private int id;
	public String type;

	// Combat stats
	public int maxHealth;
	public int currentHealth;
	public int attack;
	public int defense;
	public int specialAttack;
	public int specialDefense;
	public int speed;
	public int level;

	// Status flags
	private boolean active;
	private boolean weak;
	private boolean shiny;

	// Progression
	public int levelRequirement;
	public int xp;
	public int ivs;

	// Battle modifiers
	private static boolean random;
	private static int attackId = 0;
	private ArrayList<Attack> attacks;
	private ArrayList<State> states;

	private State principalState;
	private int accuracyStage = (int)(Math.random() * 13) - 6;
	private int evasionStage = (int)(Math.random() * 13) - 6;

	// Constants for battle calculations
	private double CRITICAL_HIT_CHANCE = 0.0417; // 4.17% standar = 0.0417
	private static final double STAGE_MODIFIER = 1.3;  // 1.5 Pokemon standar (Modify Accuracy)

	private boolean canAttack = true;
	private boolean isProtected = false;
	private boolean free = true;

	/**
	 * Default constructor for Pokemon.
	 * @throws POOBkemonException if there's an error during creation
	 */
	public Pokemon() throws POOBkemonException {
		initDefault();
	}

	/**
	 * Parameterized constructor for Pokemon.
	 * @param id Pokemon ID
	 * @param info Array containing Pokemon information
	 * @param attacksIds List of attack IDs
	 * @param random Whether to generate random stats
	 * @param pokemonLvl Initial level
	 * @throws POOBkemonException if there's an error during creation
	 */
	public Pokemon(int id, String[] info, ArrayList<Integer> attacksIds, boolean random, int pokemonLvl) throws POOBkemonException {
		try {
			if (info.length < 11) throw new POOBkemonException(POOBkemonException.LESS_INFORMACION_POKEMON);
			this.initFromParameters(id, info, attacksIds, random, pokemonLvl);
		} catch (POOBkemonException | NumberFormatException e) {
			initDefault();
			System.err.println("Error creating Pokémon: " + e.getMessage());
		}
		this.probShiny();
	}

	/**
	 * Initializes default values for a Pokemon.
	 * @throws POOBkemonException if default attack cannot be created
	 */
	private void initDefault() throws POOBkemonException {
		this.id = 0;
		this.name = "MissingNo";
		this.idPokedex = "0";
		this.type = "Normal";
		this.maxHealth = 180;
		this.currentHealth = this.maxHealth;
		this.attack = 10;
		this.defense = 10;
		this.specialAttack = 10;
		this.specialDefense = 10;
		this.speed = 10;
		this.xp = 0;
		this.level = 1;
		this.levelRequirement = 100;
		this.states = new ArrayList<State>();
		this.active = false;
		this.weak = false;
		this.random = false;
		this.ivs = 10;
		this.attacks = new ArrayList<>();
		this.attackDefault();
	}

	/**
	 * Initializes Pokemon from parameters.
	 * @param id Pokemon ID
	 * @param info Pokemon information array
	 * @param attacksIds List of attack IDs
	 * @param random Whether to generate random stats
	 * @param pokemonLvl Initial level
	 * @throws POOBkemonException if there's an error during creation
	 */
	private void initFromParameters(int id, String[] info, ArrayList<Integer> attacksIds, boolean random, int pokemonLvl) throws POOBkemonException {
		this.id = id;
		this.name = info[1];
		this.idPokedex = info[0];
		this.type = info[2];

		// Level handling
		this.level = random ? (int)(Math.random() * 31) + 25 : pokemonLvl;

		this.levelRequirement = 100;
		this.xp = 0;
		this.active = false;
		this.weak = false;
		this.random = random;
		this.attacks = new ArrayList<>(this.createAttacks(attacksIds));
		this.states = new ArrayList<State>();
		this.ivs = createRandom(32);

		// Base stats
		int baseHP = Integer.parseInt(info[5]);
		int baseAttack = Integer.parseInt(info[6]);
		int baseDefense = Integer.parseInt(info[7]);
		int baseSpAttack = Integer.parseInt(info[8]);
		int baseSpDefense = Integer.parseInt(info[9]);
		int baseSpeed = Integer.parseInt(info[10]);

		// Calculate scaled stats
		if (random) {
			this.maxHealth = calculateHPStat(baseHP, this.level, true);
			this.attack = calculateOtherStat(baseAttack, this.level, true);
			this.defense = calculateOtherStat(baseDefense, this.level, true);
			this.specialAttack = calculateOtherStat(baseSpAttack, this.level, true);
			this.specialDefense = calculateOtherStat(baseSpDefense, this.level, true);
			this.speed = calculateOtherStat(baseSpeed, this.level, true);
		} else {
			this.maxHealth = calculateHPStat(baseHP, this.level);
			this.attack = calculateOtherStat(baseAttack, this.level);
			this.defense = calculateOtherStat(baseDefense, this.level);
			this.specialAttack = calculateOtherStat(baseSpAttack, this.level);
			this.specialDefense = calculateOtherStat(baseSpDefense, this.level);
			this.speed = calculateOtherStat(baseSpeed, this.level);
		}

		this.currentHealth = this.maxHealth;
		if(this.attacks.size() == 0) {
			this.attackDefault();
		}
	}

	/**
	 * Calculates HP stat using Pokemon formula.
	 * @param baseStat Base HP stat
	 * @param level Pokemon level
	 * @param random Whether to use random IVs/EVs
	 * @return Calculated HP stat
	 */
	private int calculateHPStat(int baseStat, int level, boolean random) {
		int iv = random ? (int)(Math.random() * 32) : this.ivs;
		int ev = random ? (int)(Math.random() * 256) : 0;
		return (int)(((2 * baseStat + iv + (ev / 4)) * level) / 100) + level + 10;
	}

	/**
	 * Calculates other stats using Pokemon formula.
	 * @param baseStat Base stat value
	 * @param level Pokemon level
	 * @param random Whether to use random IVs/EVs
	 * @return Calculated stat value
	 */
	private int calculateOtherStat(int baseStat, int level, boolean random) {
		int iv = random ? (int)(Math.random() * 32) : this.ivs;
		int ev = random ? (int)(Math.random() * 256) : 0;
		return (int)(((2 * baseStat + iv + (ev / 4)) * level / 100) + 5);
	}

	// Overloaded stat calculation methods without random flag
	private int calculateHPStat(int baseStat, int level) {
		return calculateHPStat(baseStat, level, false);
	}

	private int calculateOtherStat(int baseStat, int level) {
		return calculateOtherStat(baseStat, level, false);
	}

	/**
	 * Generates a random number up to specified limit.
	 * @param limit Upper bound (exclusive)
	 * @return Random number
	 */
	public int createRandom(int limit) {
		return new Random().nextInt(limit);
	}

	// Getters and setters
	public boolean getActive() { return this.active; }
	public void setActive(boolean active) { this.active = active; }
	public int getId() { return this.id; }
	public String getName() { return this.name; }
	public boolean getWeak() { return this.weak; }
	public ArrayList<Attack> getAttacks() { return this.attacks; }

	/**
	 * Gets a specific attack by ID.
	 * @param id Attack ID
	 * @return Attack object or null if not found
	 */
	public Attack getAttack(int id) {
		for(Attack ataque : attacks) {
			if(ataque.getIdInside() == id) {
				return ataque;
			}
		}
		return null;
	}

	/**
	 * Creates attacks from IDs.
	 * @param attacksIds List of attack IDs
	 * @return List of Attack objects
	 * @throws POOBkemonException if attack creation fails
	 */
	private ArrayList<Attack> createAttacks(ArrayList<Integer> attacksIds) throws POOBkemonException {
		ArrayList<Attack> attacks = new ArrayList<>();
		MovesRepository movesRepository = new MovesRepository();
		StatusRepository statusRepository = new StatusRepository();

		for(Integer id : attacksIds) {
			String[] infoAttack = movesRepository.getAttacksId(id);

			if(infoAttack[4].equalsIgnoreCase("physical")) {
				attacks.add(new Attack(this.nextAttackId(), infoAttack));
			} else if(infoAttack[4].equalsIgnoreCase("special")) {
				attacks.add(new special(this.nextAttackId(), infoAttack));

			} else if(infoAttack[4].equalsIgnoreCase("status")) {
				String[] infoStatus = statusRepository.getStatusByName(infoAttack[9].toUpperCase());
				if(infoStatus == null) {
					infoStatus = statusRepository.getStatusByName("DEFENSE_UP");
				}
				attacks.add(new StateAttack(this.nextAttackId(), infoAttack, infoStatus));
			}
		}
		return attacks;
	}

	/**
	 * genera los siguientes Id
	 * @return
	 */
	private int nextAttackId() {
		return ++this.attackId;
	}

	/**
	 * Calculates damage dealt by an attack.
	 * @param damage Attack being used
	 * @param attacker Pokemon using the attack
	 * @return Result message string
	 * @throws POOBkemonException if damage calculation fails
	 */
	public String getDamage(Attack damage, Pokemon attacker) throws POOBkemonException {
		String message = "";
		if (!canReceiveDamage(attacker)) {
			return "";
		}

		if (damage instanceof StateAttack) {
			StateAttack stateAttack = (StateAttack) damage;
			message = handleStateAttack(stateAttack,attacker);
		} else {
			message = handleRegularAttack(damage, attacker);
		}
		return message;
	}

	private boolean canReceiveDamage(Pokemon attacker) {
		return attacker.currentHealth > 0 && this.currentHealth > 0;
	}

	/**
	 * Handles a state attack and applies corresponding effects if the state is applicable.
	 *
	 * @param stateAttack The state attack to be executed.
	 * @param attacker The Pokemon executing the state attack.
	 * @return A message describing the result of the state attack. Returns an empty string if there is no effect.
	 */
	private String handleStateAttack(StateAttack stateAttack, Pokemon attacker) {
		if (!doesStateApply(stateAttack)) {
			return attacker.name + " falló el ataque de estado! ";
		}

		StatusRepository infoState = new StatusRepository();
		String[] info = infoState.getStatusByName(stateAttack.getState());

		if (info != null) {
			return applyStatusFromInfo(info, stateAttack, attacker);
		}
		return "";
	}

	/**
	 * Determines whether a state attack's effect applies based on its accuracy.
	 *
	 * @param stateAttack The state attack being evaluated for application.
	 * @return true if the*/
	private boolean doesStateApply(StateAttack stateAttack) {
		if(stateAttack.getAccuracy()==100){return true;}
		double prob = Math.random() * 100;
		return prob < stateAttack.getAccuracy();
	}

	/**
	 * Applies a status effect to a target Pokemon based on the provided status information and state attack.
	 *
	 * @param info An array containing the status information to be applied.
	 * @param stateAttack The state attack to be applied to the Pokemon.
	 * @param attacker The Pokemon initiating the state attack.
	 * @return A string describing the outcome of the status application. Returns an empty string if an invalid status type is provided or if no effect is applied.
	 */
	private String applyStatusFromInfo(String[] info, StateAttack stateAttack, Pokemon attacker) {
		try {
			Pokemon target = stateAttack.affectsSelf() ? attacker : this;
			State estado = new State(info);
			if (!estado.isImmune(target)) {
				persistentDamage(estado,target);
				if(stateAttack.affectsSelf()){
					return " [" + target.getName() + "] se aplico " + stateAttack.getName();
				}
				return " [" + target.getName() + "] fue afectado por " + stateAttack.getName();
			}
			return target.getName() + " es inmune a " + stateAttack.getName();
		} catch (IllegalArgumentException e) {
			System.err.println("Tipo de estado inválido: " + info[0] + e.getMessage());
			return "";
		}
	}

	/**
	 * Handles the execution of a regular attack, calculates damage dealt, and updates the states of
	 * both the attacking and defending Pokemon. It also evaluates type effectiveness, attack accuracy,
	 * and updates the attacker's Power Points (PP).
	 *
	 * @param damage The attack object specifying the details of the attack being executed.
	 * @param attacker The Pokemon executing the attack.
	 * @return A string describing the result of the attack, including effectiveness, damage dealt, or
	 *         whether the attack failed.
	 * @throws POOBkemonException If an error occurs while handling the attack.
	 */
	private String handleRegularAttack(Attack damage, Pokemon attacker) throws POOBkemonException {
		MovesRepository movesRepository = new MovesRepository();
		StatsRepository statsRepository = new StatsRepository();
		String[] info = movesRepository.getAttackDamageAndType(damage.getIdCSV());

		double multiplicator = statsRepository.getMultiplier(info[0], this.type);
		if (multiplicator == 0.0) {
			attacker.spectorPP();
			return " No afecta a " + this.name + "...";
		}

		if (!doesAttackHit(damage, attacker)) {
			attacker.spectorPP();
			return attacker.name + " falló el ataque!";
		}

		double calculatedDamage = calculateDamage(damage, attacker, multiplicator);
		this.currentHealth = Math.max(0, this.currentHealth - (int)calculatedDamage);
		this.isWeak();

		attacker.spectorPP();
	
		return getDamageEffectivenessMessage(multiplicator) +
				" [" + damage.getName() + "] causó " + (int)calculatedDamage + " puntos de daño!";
	}

	/**
	 * Returns a message describing the effectiveness of damage based on
	 * the provided type effectiveness multiplier.
	 *
	 * @param multiplicator The type effectiveness multiplier of the attack.
	 *                      For example, 2.0 indicates the attack is super effective,
	 *                      0.5 indicates it is not very effective, and other values
	 *                      indicate neutral effectiveness.
	 * @return A string message indicating the effectiveness of the attack.
	 *         Returns " ¡Fue super efectivo! \n" if the multiplier is 2.0,
	 *         " No fue muy efectivo... \n" if it is 0.5, and an empty string
	 *         for other multiplier values.
	 */
	private String getDamageEffectivenessMessage(double multiplicator) {
		if (multiplicator == 2.0) {
			return " ¡Fue super efectivo! \n";
		} else if (multiplicator == 0.5) {
			return " No fue muy efectivo... \n";
		}
		return "";
	}

	/**
	 * Determines if the entity is in a weak state based on its current health.
	 * If the current health is less than or equal to zero, the health is set to zero
	 * and the entity is marked as weak.
	 */
	public void isWeak(){
		if (this.currentHealth <= 0) {
			this.currentHealth = 0;
			this.weak = true;
		}
	}

	/**
	 * Determines if an attack hits.
	 * @param damage Attack being used
	 * @param attacker Pokemon using the attack
	 * @return true if attack hits, false otherwise
	 */
	private boolean doesAttackHit(Attack damage, Pokemon attacker) {
		if (damage.getAccuracy() >= 100) return true;

		double hitProbability = (damage.getAccuracy() / 100.0) *
				(Math.pow(STAGE_MODIFIER, attacker.accuracyStage) / Math.pow(STAGE_MODIFIER, -this.evasionStage));

		hitProbability *= (damage.getAccuracy() <= 30) ? 0.6 : 1.0;
		return Math.random() < Math.max(0.1, Math.min(1.0, hitProbability));
	}

	/**
	 * Calculates damage using Pokemon formula.
	 * @param damage Attack being used
	 * @param attacker Pokemon using the attack
	 * @param typeEffectiveness Type effectiveness multiplier
	 * @return Calculated damage
	 */
	private double calculateDamage(Attack damage, Pokemon attacker, double typeEffectiveness) {
		if (damage instanceof StateAttack) {
			return 0;
		}

		int power = damage.getPower();
		int level = attacker.level;
		double randomFactor = 0.85 + (Math.random() * 0.15);
		double critical = (Math.random() < CRITICAL_HIT_CHANCE) ? 2 : 1.0;

		// Determinar qué estadísticas usar (físicas o especiales)
		boolean isSpecialAttack = damage instanceof special;
		double attackStat = isSpecialAttack ? attacker.specialAttack : attacker.attack;
		double defenseStat = isSpecialAttack ? this.specialDefense : this.defense;

		// Fórmula de daño estándar de Pokémon
		double damageValue = (((2 * level / 5 + 2) * power * attackStat / defenseStat) / 50 + 2);
		damageValue *= critical * typeEffectiveness * randomFactor;

		// Asegurar mínimo 1 de daño
		return Math.max(1, Math.round(damageValue));
	}

	/**
	 * Manages the application of persistent damage effects to a target Pokémon based on the given attack state.
	 *
	 * @param attackStateRival The state of the attack or condition being applied by the rival Pokémon.
	 * @param target The target Pokémon on which the damage or condition is being applied.
	 */
	public void persistentDamage(State attackStateRival, Pokemon target) {
		if (shouldBecomePrincipalState(attackStateRival, target)) {
			setAsPrincipalState(attackStateRival, target);
		} else if (shouldBeAddedAsSecondaryState(attackStateRival)) {
			addAsSecondaryState(attackStateRival, target);
		}
	}

	/**
	 * Determines if the given state should become the principal state based on the target conditions.
	 *
	 * @param state the current state to evaluate
	 * @param target the target object to assess for principal state eligibility
	 * @return true if the target's principal is null and the state is a principal state, otherwise false
	 */
	private boolean shouldBecomePrincipalState(State state, Pokemon target) {
		return target.principalIsNull() && state.isPrincipal();
	}

	/**
	 * Checks if the principalState is null.
	 *
	 * @return true if the principalState is null, false otherwise
	 */
	private boolean principalIsNull(){
		return this.principalState == null;
	}

	/**
	 * Sets the provided state as the principal state for the given target Pokemon.
	 *
	 * @param state the state to be set as the principal state
	 * @param target the Pokemon object on which the state will be set
	 */
	private void setAsPrincipalState(State state, Pokemon target) {
		target.addPrincipalState(state);
	}

	/**
	 * Determines if the given state should be added as a secondary state.
	 *
	 * @param state the state to be evaluated
	 * @return true if the state is not a principal state, false otherwise
	 */
	private boolean shouldBeAddedAsSecondaryState(State state) {
		return !state.isPrincipal();
	}

	/**
	 * Adds the given state as a secondary state to the specified target.
	 *
	 * @param state  the secondary state to be added
	 * @param target the Pokemon to which the secondary state will be applied
	 */
	private void addAsSecondaryState(State state,Pokemon target) {
		target.addSecundariState(state);
	}

	/**
	 * Adds a secondary state to the collection of states.
	 *
	 * @param state the State object to be added to the collection
	 */
	public void addSecundariState(State state){
		this.states.add(state);
	}

	/**
	 * Sets the principal state of the object to the specified state.
	 *
	 * @param state the State object to be set as the principal state
	 */
	public void addPrincipalState(State state) {
		this.principalState = state;
	}

	/**
	 * Obtiene la información del Pokémon en un arreglo de Strings.
	 * @return Arreglo con toda la información del Pokémon
	 */
	public String[] getInfo() {
		return new String[] {
				String.valueOf(this.id),           // 0 - ID
				this.name,                         // 1 - Nombre
				this.idPokedex,                    // 2 - ID Pokédex
				this.type,                         // 3 - Tipo
				String.valueOf(this.level),        // 4 - Nivel
				String.valueOf(this.maxHealth),    // 5 - Vida máxima
				String.valueOf(this.currentHealth), // 6 - Vida actual
				String.valueOf(this.attack),        // 7 - Ataque
				String.valueOf(this.defense),      // 8 - Defensa
				String.valueOf(this.specialAttack), // 9 - Ataque Especial
				String.valueOf(this.specialDefense),// 10 - Defensa Especial
				String.valueOf(this.speed),         // 11 - Velocidad
				String.valueOf(this.xp),            // 12 - XP actual
				String.valueOf(this.levelRequirement), // 13 - XP requerido
				String.valueOf(this.active),        // 14 - Estado (activo)
				String.valueOf(this.weak),          // 15 - Pokemon debilitado
				String.valueOf(this.shiny),          // 16 - Pokemon shiny
				String.valueOf(this.principalState)  //17 - Estado principal del pokemon.
		};
	}


	/**
	 * Obtiene la información de todos los ataques del Pokémon.
	 * @return Matriz con la información de cada ataque
	 */
	public String[][] getAttacksInfo() {
		int attacksSize = attacks.size();
		String[][] attacksInfo = new String[attacksSize][9];

		for (int i = 0; i < attacksSize; i++) {
			Attack attack = attacks.get(i);
			if (attack != null) {
				attacksInfo[i] = attack.getInfo();
			} else {
				attacksInfo[i] = new String[]{
						"Desconocido",  // nombre
						"Normal",       // tipo
						"0",            // poder
						"0",            // precisión
						"0",            // pp
						"0",            // id
						""              // descripción
				};
			}
		}
		return attacksInfo;
	}

	/**
	 * Cura al Pokémon una cantidad específica de HP.
	 * @param heal Cantidad de HP a curar
	 */
	public void heals(int heal) {
		if(!this.weak){
			this.currentHealth = this.currentHealth + heal;
			if(this.currentHealth > this.maxHealth) {
				this.currentHealth = this.maxHealth;
			}
		}
	}

	/**
	 * Revive al Pokémon con la mitad de su vida máxima.
	 */
	private void revive() {
		if(this.currentHealth == 0) {
			this.currentHealth = this.maxHealth/2;
		}
		this.weak = false;
		this.principalState = null;
	}

	/**
	 * Aplica un efecto al Pokémon según la información proporcionada.
	 * @param info Información del efecto a aplicar
	 */
	public void effect(String[] info) {
		if(info[0].equalsIgnoreCase("Potion") || info[0].equalsIgnoreCase("Revive")) {
			if(info[1].equalsIgnoreCase("Heals")) {
				this.heals(Integer.parseInt(info[2]));
			} else if (info[1].equalsIgnoreCase("Revive")) {
				this.revive();
			}
		}
	}

	/**
	 * Initializes default attack when no attacks are available.
	 * @throws POOBkemonException if default attack cannot be created
	 */
	private void attackDefault() throws POOBkemonException {
		ArrayList<Integer> unickAttack = new ArrayList<>();
		unickAttack.add(357);
		this.attacks = this.createAttacks(unickAttack);
		if(this.attacks.size() == 0) throw new POOBkemonException("Ataque no creado.");
	}

	/**
	 * Checks and resets PP if all attacks are exhausted.
	 * @throws POOBkemonException if default attack cannot be created
	 */
	private void spectorPP() throws POOBkemonException {
		boolean hasPP = false;
		for(Attack attack: this.attacks) {
			if(attack.getPPActual() > 0) {
				hasPP = true;
				break;
			}
		}
		if(!hasPP) {
			this.attacks.clear();
			this.attackDefault();
		}
	}


	/**
	 * Applies damage to the entity by reducing its current health. If the health reaches or falls below zero,
	 * sets the current health to zero and marks the entity as weak.
	 *
	 * @param damage The amount of damage to subtract from the entity's current health.
	 */
	public void takeDamage( int damage ) {
		this.currentHealth -= damage;
		if(this.currentHealth <= 0) {
			this.currentHealth = 0;
			this.weak = true;
		}
	}

	public int getCurrentHealth() {
		return this.currentHealth;
	}

	/**
	 * Determines if Pokemon is shiny (10% chance).
	 */
	private void probShiny() {
		this.shiny = Math.random() < 0.1;
	}

	/**
	 * Decision auntomatica si se le acaba el tiempo al entrenador
	 */
	public void timeOver() {
		for (Attack at : this.attacks) {
			at.usePP();
		}
	}

	/**
	 * Determines whether a state with the specified name exists in the collection of states.
	 *
	 * @param stateName the name of the state to check for. It is case-insensitive.
	 * @return true if a state with the specified name exists, otherwise false.
	 */
	public boolean hasState(String stateName) {
		for (State s : states) {
			if (s.getName().equalsIgnoreCase(stateName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the protected status of an object.
	 *
	 * @param protect a boolean value indicating whether the object should be protected (true) or not (false)
	 */
	public void setProtected(boolean protect){
		this.isProtected = protect;
	}
	/**
	 * Reduce la velocidad del Pokémon en un porcentaje específico.
	 * @param percentReduce Porcentaje de reducción (ej: 50 para reducir 50%)
	 */
	public void reduceSpeed(int percentReduce) {
		this.speed = this.speed - (this.speed * percentReduce / 100);
	}
	
	public String getType() {
		return type;
	}

	public void setCanAttack(boolean active){
		this.canAttack = active;
	}

	public boolean canAttack() {
		return this.canAttack;
	}

	public int getEvasionStage() {
		return this.evasionStage;
	}

	public int getAccuracyStage() {
		return this.accuracyStage;
	}

	/**
	 * Modifies the specified stat of the object by a given multiplicator.
	 *
	 * @param stat the name of the stat to modify.
	 *             Accepted values are "attack", "defense", "speed",
	 *             "SP_defense", "SP_attack", "Critico", and "evasion".
	 * @param multiplicator the factor by which the specified stat will be multiplied.
	 */
	public void modifyStat(String stat, double multiplicator){
		switch (stat){
			case "attack":
				this.attack = (int)(this.attack*multiplicator);
				break;
			case "defense":
				this.defense = (int)(this.defense*multiplicator);
				break;
			case "speed":
				this.speed = (int)(this.speed*multiplicator);
				break;
			case "SP_defense":
				this.specialDefense = (int)(this.specialDefense*multiplicator);
				break;
			case "SP_attack":
				this.specialAttack = (int)(this.specialAttack*multiplicator);
				break;
			case "Critico":
				this.CRITICAL_HIT_CHANCE = this.CRITICAL_HIT_CHANCE*multiplicator;
				break;
			case "evasion":
				this.evasionStage = (int)(this.evasionStage*multiplicator);
				break;
		}
	}

	/**
	 * Disables the last move in the attacks list by setting its current PP to 0.
	 * This method accesses the last element in the attacks list,
	 * retrieves it, and modifies its PP (Power Points) value to indicate
	 * it can no longer be used.
	 *
	 * Assumes that the `attacks` list is non-empty when this method is called.
	 * If the list is empty, this may result in an IndexOutOfBoundsException.
	 */
	public void disableLastMove(){
		this.attacks.get(this.attacks.size()-1).setPPActual(0);
	}
	public void setTrapped(boolean tramp){
		this.free = tramp;
	}
	public void setNewPS(int PS){
		this.maxHealth = this.maxHealth + PS;
	}
	public boolean isProtected(){
		return this.isProtected;
	}
	public boolean isFree(){
		return this.free;
	}
	private boolean activeState(){
		return (true);
	}

	/**
	 * Applies the current state logic to the object.
	 *
	 * This method evaluates and applies effects based on the principal state and
	 * additionally iterates through all secondary states, applying their respective effects.
	 * It performs the following operations:
	 * 1. Checks if a principal state exists and is active. If so, it applies the
	 *    corresponding effect.
	 * 2. Clears the principal state if its duration has ended.
	 * 3. Cleans up states by invoking the `statesDelete` method.
	 * 4. Applies the effect of each state in the collection of states.
	 */
	public void applyState(){
		if(!(this.principalState == null) && this.activeState()) {
			this.principalState.applyEffect(this);
		}else if(this.principalState != null && this.principalState.getDuration() == 0){
			principalState = null;
		}
		statesDelete();
		for (State s : states) {
			s.applyEffect(this);
		}
	}

	/**
	 * Deletes the provided state. If the provided state is the current principal state,
	 * it nullifies the principal state.
	 *
	 * @param state the state to be deleted
	 */
	public void deleteState(State state){
		if(this.principalState == state){
			principalState = null;
		}
	}

	/**
	 * Removes states from the list whose duration is zero.
	 *
	 * This method iterates through the list of states and deletes any state
	 * where the duration equals zero. The iteration continues until all
	 * elements of the list are checked. If a state is removed during
	 * iteration, the loop does not increment the counter to re-check the
	 * shifted element at the current index. If no state meets the removal
	 * criteria, all the states are retained, and the method completes without
	 * making changes to the list.
	 */
	private void statesDelete(){
		int stateCount = 0;
		while(stateCount < this.states.size()){
			if(this.states.get(stateCount).getDuration() == 0){
				this.states.remove(stateCount);
			}else{
				stateCount ++;
			}
		}
	}
}