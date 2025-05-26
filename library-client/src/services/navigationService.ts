import { useRouter } from 'vue-router'

export function useNavigation() {
    const router = useRouter()

    const navigateTo = (link: string) => {
        link ? router.push(link) : console.error('No link provided');
    }

    return { navigateTo }
}