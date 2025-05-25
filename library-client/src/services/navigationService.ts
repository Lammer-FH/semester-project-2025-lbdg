import { useRouter } from 'vue-router'

export function useNavigation() {
    const router = useRouter()

    const navigateTo = (link: string) => {
        if (link) router.push(link)
    }

    return { navigateTo }
}