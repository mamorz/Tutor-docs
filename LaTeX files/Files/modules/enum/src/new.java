// Neuer enumerate-Typ mit Bezeichnung "Members":
public enum @@cMembers@@ { JERRY, BOBBY, PHIL };

public @@cMembers@@ selectedBandMember;

// Enum-Variable kann einen der definierten Werte annehmen
selectedBandMember = @@cMembers@@.BOBBY;

if (selectedBandMember == @@cMembers@@.JERRY) {
    // Fuehre Code fuer JERRY aus
}
