import logging
from recommender.recommender import generate_recommendations

logger = logging.getLogger(__name__)


def run_recommender_service():
    """
    Entry point for running the recommender service.
    Can be triggered by a cron job, API call, or CLI.
    """
    try:
        result = generate_recommendations()
        logger.info("Recommender run finished: %s", result)
        return result
    except Exception as e:
        logger.exception("Fatal error running recommender service")
        return {"status": "error", "message": str(e)}
