export const TORAH = {
    GENESIS: 'TORAH/Genesis',
    EXODUS: 'TORAH/Exodus',
    LEVITICUS: 'TORAH/Leviticus',
    NUMBERS: 'TORAH/Numbers',
    DEUTERONOMY: 'TORAH/Deuteronomy'
};

export const PROPHETS = {
    JOSHUA: 'PROPHETS/Joshua',
    JUDGES_I: 'PROPHETS/Judges 1',
    JUDGES_II: 'PROPHETS/Judges 2',
    SAMUEL_I: 'PROPHETS/Samuel 1',
    SAMUEL_II: 'PROPHETS/Samuel 2',
    KINGS_I: 'PROPHETS/Kings 1',
    KINGS_II: 'PROPHETS/Kings 2',
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

export function stripPrefix(pathWithPrefix: string): string{
    return pathWithPrefix.split("/")[1];
}

