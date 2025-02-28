package com.medicare.data

object InfoData {

    private val guidelines = arrayListOf<Pair<String,String>>(
        Pair("🗣️ Patient Consent",
            "✅ Confirm the patient’s identity (name, age, and any ID if required).\n" +
                    "✅ Obtain verbal or digital consent for the teleconsultation.\n" +
                    "✅ Inform the patient about the limitations and scope of telemedicine."),
        Pair("📝 Conducting the Consultation",
            "✅ Ask for detailed symptoms, medical history, and recent medications.\n" +
                    "✅ Guide the patient through any physical self-examinations if necessary.\n" +
                    "✅ Record all findings and discussions in the patient’s medical records."),
        Pair("💊 Prescription & Treatment",
            "✅ Prescribe only if confident about the diagnosis.\n" +
                    "✅ Clearly explain medication usage, dosage, and possible side effects.\n" +
                    "✅ Avoid prescribing controlled substances unless absolutely necessary and allowed by law."),
        Pair("🧾 Documentation & Privacy",
            "✅ Document the consultation thoroughly, including consent and advice given.\n" +
                    "✅ Protect patient data and adhere to privacy regulations (e.g., HIPAA compliance).\n" +
                    "✅ Ensure any shared documents or prescriptions are sent through secure channels."),
        Pair("📅 Follow-Up Care",
            "✅ Schedule follow-up appointments if required.\n" +
                    "✅ Provide guidance on next steps, including referrals or lab tests.\n" +
                    "✅ Encourage patients to reach out if symptoms worsen or change."),
        Pair("🚨 Emergency Protocols",
            "✅ Patients experience chest pain, severe shortness of breath, or sudden weakness.\n" +
                    "✅ Symptoms suggesting a life-threatening condition.\n" +
                    "✅ Advise immediate in-person care if teleconsultation isn’t sufficient."),
        Pair("🤝 Professional Conduct",
            "✅ Be respectful, non-judgmental, and patient during consultations.\n" +
                    "✅ Maintain punctuality and keep consultations within allotted times.\n" +
                    "✅ Refrain from multitasking during patient consultations."),
        Pair("🧘 Self-Care for Doctors",
            "✅ Take regular breaks between consultations.\n" +
                    "✅ Stay hydrated and manage your mental health.\n" +
                    "✅ Reach out to peers or support networks when needed.")
    )

    private val wellness = arrayListOf<Pair<String,String>>(
        Pair("🩺 Health Tips",
            "✅ **Stay hydrated** : Drink at least 8 glasses of water daily.\n" +
                    "✅ Get 7–9 hours of quality sleep each night.\n" +
                    "✅ Wash hands regularly to prevent infections.\n" +
                    "✅ Take short breaks from screens to reduce eye strain.\n" +
                    "✅ Maintain good posture to prevent back and neck pain.\n" +
                    "✅ Limit sugar, salt, and processed food intake.\n" +
                    "✅ Schedule regular health check-ups.\n" +
                    "✅ Spend time outdoors for fresh air and sunlight."),
        Pair("🏋️ Fitness Plans",
            "🔥 Beginner Plan:\n"+
                    "     🏃‍♂️ 20-min brisk walk daily.\n"+
                    "     🧘‍♀️ Light stretching for 10 mins daily.\n"+
                    "     🚶 Take stairs instead of elevators.\n"+
                    "💪 Intermediate Plan:\n"+
                    "     🏋️‍♀️ Strength training .\n"+
                    "     🚴 Cardio exercises .\n"+
                    "     🧘‍♂️ Core strengthening .\n"+
                    "🏆 Workout Goals:\n"+
                    "     🎯 Increase daily steps to 10,000.\n"+
                    "     🎯 Daily stretching.\n"+
                    "     🎯 Interval training.\n"),
        Pair("🧘 Meditation & Mindfulness",
            "🕯️ 5-Minute Morning Meditation: Start your day with calm breathing.\n"+
                    "🌙 Evening Relaxation: Guided audio to unwind before bed.\n"+
                    "🎧 Focus Sessions: Improve concentration with short mindfulness exercises.\n"+
                    "🫁 Breathing Techniques: Simple methods to reduce stress quickly.\n"+
                    "📝 Gratitude Practice: Write down 3 things you're grateful for daily.\n"+
                    "🧘 Mindful Walking: Focus on your steps and breathing during walks."),
        Pair("🥗 Diet & Nutrition",
            "🍽️ Healthy Eating Tips:\n"+
                    "     🥦 Include fruits and vegetables.\n"+
                    "     🥤 Stay hydrated.\n"+
                    "     🥜 Snack on nuts .\n"+
                    "     🥗 Opt for whole grains.\n"+
                    "🥣 Sample Meal Plan:\n"+
                    "     ✅ Breakfast: Oatmeal.\n"+
                    "     ✅ Lunch: Grilled chicken and salad.\n"+
                    "     ✅ Snack: Yogurt with almonds.\n"+
                    "     ✅ Dinner: Vegetables with fish.\n"+
                    "🥕 Additional Info:\n"+
                    "     🧂 Limit salt.")
    )

    fun getGuidelines():ArrayList<Pair<String,String>>{
        return  guidelines
    }

    fun getWellnessList(): ArrayList<Pair<String, String>>{
        return wellness
    }
}