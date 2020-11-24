

export const TORAH = {
    GENESIS: 'TORAH/Genesis',
    EXODUS: 'TORAH/Exodus',
    LEVITICUS: 'TORAH/Leviticus',
    NUMBERS: 'TORAH/Numbers',
    DEUTERONOMY: 'TORAH/Deuteronomy'
};

export const PROPHETS = {
    JOSHUA: 'PROPHETS/Joshua',
    JUDGES_I: 'PROPHETS/Judges',
    SAMUEL_I: 'PROPHETS/Samuel I',
    SAMUEL_II: 'PROPHETS/Samuel II',
    KINGS_I: 'PROPHETS/Kings I',
    KINGS_II: 'PROPHETS/Kings II',
    ISAIAH: 'PROPHETS/Isaiah',
    JEREMIAH: 'PROPHETS/Jeremiah',
    EZEKIEL: 'PROPHETS/Ezekiel',
    HOSEA: 'PROPHETS/Hosea',
    JOEL: 'PROPHETS/Joel',
    AMOS: 'PROPHETS/Amos',
    OBADIAH: 'PROPHETS/Obadiah',
    JONAH: 'PROPHETS/Jonah',
    MICHA: 'PROPHETS/Micha',
    NAHUM: 'PROPHETS/Nahum',
    HABAKKUK: 'PROPHETS/Habakkuk',
    ZEPHANIAH: 'PROPHETS/Zephania',
    HAGGAI: 'PROPHETS/Haggai',
    ZECHARIAH: 'PROPHETS/Zechariah',
    MALACHI: 'PROPHETS/Malachi',
    DANIEL: 'PROPHETS/Daniel',
    EZRA: 'PROPHETS/Ezra',
    NEHEMIAH: 'PROPHETS/Nehemiah',
    CHRONICLES: 'PROPHETS/Chronicles'
};

export const WRITINGS = {
    PSALMS: 'WRITINGS/Psalms',
    PROVERBS: 'WRITINGS/Proverbs',
    JOB: 'WRITINGS/Job',
    RUTH: 'WRITINGS/Ruth',
    LAMENTATIONS: 'WRITINGS/Lamentations',
    ECCLESIASTES: 'WRITINGS/Ecclesiastes',
    ESTHER: 'WRITINGS/Esther'
}

/*interface YDictionary {
    [index: string]: string;
}*/
 export const HEBREW_NAMES_DICT = {
    'TORAH/Genesis': 'בראשית',
    'TORAH/Exodus': 'שמות',
    'TORAH/Leviticus': 'ויקרא',
    'TORAH/Numbers': 'במדבר',
    'TORAH/Deuteronomy': 'דברים',

    'PROPHETS/Joshua': 'יהושוע',
    'PROPHETS/Judges': 'שופטים',
    'PROPHETS/Samuel I': 'שמואל א',
    'PROPHETS/Samuel II': 'שמואל ב',
    'PROPHETS/Kings I': 'מלכים א',
    'PROPHETS/Kings II': 'מלכים ב',
    'PROPHETS/Isaiah': 'ישעיה',
    'PROPHETS/Jeremiah': 'ירמיהו',
    'PROPHETS/Ezekiel': 'יחזקאל',
    'PROPHETS/Hosea': 'הושע',
    'PROPHETS/Joel': 'יואל',
    'PROPHETS/Amos': 'עמוס',
    'PROPHETS/Obadiah': 'עובדיה',
    'PROPHETS/Jonah': 'יונה',
    'PROPHETS/Micha': 'מיכה',
    'PROPHETS/Nahum': 'נחום',
    'PROPHETS/Habakkuk': 'חבקוק',
    'PROPHETS/Zephania': 'צפניה',
    'PROPHETS/Haggai': 'חגי',
    'PROPHETS/Zechariah': 'זכריה',
    'PROPHETS/Malachi': 'מלכי',
    'PROPHETS/Daniel': 'דניאל',
    'PROPHETS/Ezra': 'עזרא',
    'PROPHETS/Nehemiah': 'נחמיה',
    'PROPHETS/Chronicles': 'דברי הימים',

    'WRITINGS/Psalms': 'תהלים',
    'WRITINGS/Proverbs': 'משלי',
    'WRITINGS/Job': 'איוב',
    'WRITINGS/Ruth': 'רות',
    'WRITINGS/Lamentations': 'איכה',
    'WRITINGS/Ecclesiastes': 'קוהלת',
    'WRITINGS/Esther': 'אסתר'
}

export function stripPrefix(pathWithPrefix: string): string{
    return pathWithPrefix.split("/")[1];
}

export function toEnglishBookName(hebrewName: string): string{
    for (const [key, value] of Object.entries(HEBREW_NAMES_DICT)){
        if(`${value}`.toString() === hebrewName){ //this.bookList.push(`${value}`.toString());
            return `${key}`.toString();
        }
    }
    return "null";
}
export function toHebrewBookName(englishName: string): string{
    for (const [key, value] of Object.entries(HEBREW_NAMES_DICT)){
        if(`${key}`.toString() === englishName){ //this.bookList.push(`${value}`.toString());
            return `${value}`.toString();
        }
    }
    return "null";
}

export function hebrewBooksInTorah(){
    return Object.keys(TORAH).map(bookName => toHebrewBookName(bookName))
}

export function hebrewBooksInProphets(){
    return Object.keys(PROPHETS).map(bookName => toHebrewBookName(bookName))
}

export function hebrewBooksInWritings(){
    return Object.keys(WRITINGS).map(bookName => toHebrewBookName(bookName))
}


