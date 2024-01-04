package com.splendor.constants;

public final class Messages {

    public static final String TITLE = "Splendor Game";
    public static final String HUMAN = "Human player";
    public static final String ROBOT = "Robot player {0}";

    public static final String INPUT_ERROR = "Erreur de saisie : {0}";

    public static final String OPTION_A = "A: Prendre 2 gemmes identiques";
    public static final String OPTION_B = "B: Prendre 3 gemmes différentes";
    public static final String OPTION_C = "C: Acheter une carte";
    public static final String OPTION_D = "D: Réserver une carte";
    public static final String OPTION_E = "E: Passer son tour";

    public static final String RESOURCE_SELECTION = "Quelle ressource ?";
    public static final String RESOURCES_SELECTION = "Quelles ressources ?";
    public static final String POSITION_SELECTION = "Quelle position ?";
    public static final String ACTION_SELECTION = "Quelle action ?";
    public static final String TAKE_YOUR_TURN = 
        "Vous avez choisi de passer votre tour.";

    public static final String BUY_CARD_MESSAGE =
        "Entrez le tier et la colonne séparés par un espace."
        + "\nExemple : 1 2 (pour la carte tier 1, colonne 2).";
    
    public static final String CARD_MESSAGE = 
        "Entrez le tier et la colonne séparés par un espace."
        + "\nExemple : 1 2 (pour la carte tier 1, colonne 2).";

    public static final String RESERVE_CARD_MESSAGE = 
        "Vous pouvez aussi réserver une carte d'un deck en entrant seulement "
        + "le tier. \nExemple : 1 (pour réserver une carte tier 1).";

    public static final String SAME_TOKEN_MESSAGE = 
        "Entrez la lettre correspondant à la ressource."
        + "\nExemple : A (pour la ressource EMERALD).";

    public static final String DIFFERENT_TOKEN_MESSAGE =
        "Entrez les lettres correspondant aux ressources séparées par un "
        + "espace. \nExemple : A C E (pour les ressources EMERALD, SAPPHIRE "
        + "et RUBY).";

    public static final String DISCARD_TOKEN_MESSAGE =
        "Entrez les lettres correspondant aux ressources séparées par un "
        + "espace. \nExemple : A A B E (pour les ressources EMERALD, "
        + "EMERALD, DIAMOND et RUBY).";

    public static final String SAME_TOKEN_ERROR =
        "Vous devez entrer une seule lettre correspondant à une ressource";

    public static final String DIFFERENT_TOKEN_ERROR =
        "Vous devez entrer trois lettres correspondant aux "
        + "ressources séparées par un espace.";
    
    public static final String DISCARD_TOKEN_SUPPLY_ERROR = 
        "Vous devez entrer {0} lettres correspondant aux ressources "
        + "séparées par un espace.";
    
    public static final String DISCARD_TOKEN_ERROR =
        "Vous devez entrer des lettres correspondant aux ressources.";

    public static final String NOT_ENOUGH_GEMS = 
        "Il n'y a pas assez de gemmes disponibles.";

    public static final String NOT_ENOUGH_RESOURCES =
        "Vous n'avez pas assez de ressources à défausser.";
    
    public static final String RESERVED_CARDS = 
        "Vous avez déjà réservé les cartes suivantes :";
    
    public static final String BUY_RESERVED_CARD = 
        "Vous avez la possibilité d'en acheter une en entrant "
        + "R et le numéro de la carte séparés par un espace.";
    
    public static final String BUY_CARD_INVALID_INPUT =
        "Vous devez entrer deux nombres séparés par un espace, ou R "
        + "suivi du nombre de la carte si vous en avez réservé une.";
    
    public static final String CARD_INVALID_TIER =
        "Le tier doit être compris entre 1 et " + Values.TIER_NUMBER + ".";

    public static final String CARD_INVALID_COLUMN =
        "La colonne doit être comprise entre 1 et "
        + Values.COLUMN_NUMBER + ".";
    
    public static final String CARD_INVALID_NUMBER = 
        "Le numéro doit être compris entre 1 et "
        + Values.MAX_RESERVED_CARDS + ".";
    
    public static final String CARD_EMPTY_SLOT =
        "Il n'y a pas de carte à cette position.";
    
    public static final String CARD_READER_ERROR =
        "Impossible de lire le fichier contenant les ressources";
    
    public static final String BUY_CARD_NOT_ENOUGH_RESOURCES = 
        "Vous n'avez pas assez de ressources pour acheter cette carte.";
    
    public static final String RESERVE_CARD_INVALID_INPUT = 
        "Vous devez entrer le tier et la colonne séparés par un espace, "
        + "ou seulement le tier pour réserver une carte d'un deck.";
    
    public static final String RESERVE_CARD_LIMIT_ERROR =
        "Vous avez déjà le nombre maximum de cartes réservées.";
    
    public static final String SLOT_NOT_EMPTY =
        "Il reste encore une carte à cette emplacement.";

    public static final String PLAYERS_NUMBER_ERROR =
        "Le nombre de joueur doit être entre 2 et 4 inclus.";

    public static final String WIN = "{0} a gagné la partie";
    public static final String WINS = "{0} ont gagné la partie";

    public static final String AVAILABLE_RESOURCES = 
        "Resources disponibles : ";
    
    public static final String INVALID_TIER =
        "Tier invalide. Assurez-vous que le niveau spécifié se trouve "
        + "dans les limites de la pile de cartes";
}
